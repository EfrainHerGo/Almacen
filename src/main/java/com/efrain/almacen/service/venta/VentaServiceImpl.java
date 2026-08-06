package com.efrain.almacen.service.venta;

import com.efrain.almacen.dto.ventas.ReporteVentasResponse;
import com.efrain.almacen.dto.ventas.VentaRequest;
import com.efrain.almacen.dto.ventas.VentaResponse;
import com.efrain.almacen.endentities.DetalleVenta;
import com.efrain.almacen.endentities.Producto;
import com.efrain.almacen.endentities.Sucursales;
import com.efrain.almacen.endentities.Venta;
import com.efrain.almacen.enums.EstadoVenta;
import com.efrain.almacen.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.almacen.mappers.DetalleVentaMapper;
import com.efrain.almacen.mappers.VentaMapper;
import com.efrain.almacen.repositories.ProductoRepository;
import com.efrain.almacen.repositories.Sucursalesrepository;
import com.efrain.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final Sucursalesrepository sucursalesRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<VentaResponse> listarActivas() {
        log.info("Listando ventas activas (REGISTRADAS)");
        return ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA).stream()
                .map(ventaMapper::entidadResponse)
                .toList();
    }
    /*Hace consulta y regresa  los que estan en estado registrada
     * lo que hace es encontrar el estado de venta con el estado venta por
     * defecto de registrada, se recorre con stream se recorre la lista
     * map tranforma los datos
     * toList es la funcion terminal*/

    @Transactional(readOnly = true)
    @Override
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas canceladas");
        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA).stream()
                .map(ventaMapper::entidadResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Buscando venta activa con id: {}", id);
        Venta venta = ventaRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .orElseThrow(() -> new RecursoNoEncontradoExceptions("No se encontró una venta activa con el ID: " + id));
        return ventaMapper.entidadResponse(venta);
    }
    /*
    * Se registra las ventas
    * se busca sucursal por id en caso de que no encuentre regresa con el or.Elsethrow
    * Se construye venda */
    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Iniciando el registro de venta para la sucursal ID: {}", request.idSucursal());

        // Validar y obtener la Sucursal
        Sucursales sucursal = sucursalesRepository.findById(request.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoExceptions("Sucursal no encontrada con id: " + request.idSucursal()));

        Venta venta = Venta.builder()
                .sucursales(sucursal)
                .fecha(LocalDate.now())
                .estadoVenta(EstadoVenta.REGISTRADA)
                .detalleVentas(new ArrayList<>())
                .build();
        /*Se hace una lista de detalle venta con productos y recorriendolo
        * se mapea item con la declaracion de producto asignado la id que en este caso es el item
        * se valida la cantidad existente en la base de datos y se descuenta
        * y se crea la lista de detalle venta con los datos solicitados */


        // Mapear cada producto a DetalleVenta y descontar Stock
        List<DetalleVenta> detalles = request.productos().stream()
                .map(item -> {
                    Producto producto = productoRepository.findById(item.idProducto())
                            .orElseThrow(() -> new RecursoNoEncontradoExceptions("Producto no encontrado con id: " + item.idProducto()));

                    // Validar Stock
                    if (producto.getCantidad() < item.cantidadProducto()) {
                        throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
                    }
                    // Descontar la cantidad vendida
                    producto.descontarCantidad(item.cantidadProducto());
                    return DetalleVenta.builder()
                            .venta(venta)
                            .producto(producto)
                            .cantidadProducto(item.cantidadProducto())
                            .precioProducto(producto.getPrecio())
                            .build();
                })
                .toList();

        // Vincular los detalles a la venta
        venta.getDetalleVentas().addAll(detalles);
        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta registrada con éxito. ID asignado: {}", ventaGuardada.getId());

        return ventaMapper.entidadResponse(ventaGuardada);
    }
    @Override
    public VentaResponse cancelar(Long id) {
        log.info("Cancelando la venta con ID: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoExceptions("Venta no encontrada con id: " + id));

        // Ejecuta validación interna del estado
        venta.cancelar();

        // Reabastecer el stock de los productos vendidos
        if (venta.getDetalleVentas() != null) {
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                Producto producto = detalle.getProducto();
                producto.aunmentarCantidad(detalle.getCantidadProducto());
            }
        }

        log.info("Venta ID: {} cancelada y stock reembolsado exitosamente", id);
        return ventaMapper.entidadResponse(venta);
    }

    @Transactional(readOnly = true)
    public List<ReporteVentasResponse> reporte() {
        log.info("Generando reporte agregado de ventas por sucursal desde BD");
        /*
        En este caso se pasa la proyeccion que es lo que arma el reporte
        y ya pone la funcion donde se crea el reporte
        */
        return ventaMapper.projectionAReponse(ventaRepository.obtentenerReporteVentas(EstadoVenta.REGISTRADA));
    }

}