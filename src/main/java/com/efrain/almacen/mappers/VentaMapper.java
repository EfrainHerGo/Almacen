package com.efrain.almacen.mappers;

import com.efrain.almacen.dto.ventas.*;
import com.efrain.almacen.endentities.DetalleVenta;
import com.efrain.almacen.endentities.Producto;
import com.efrain.almacen.endentities.Sucursales;
import com.efrain.almacen.endentities.Venta;
import com.efrain.almacen.enums.EstadoVenta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VentaMapper {

    private final DetalleVentaMapper detalleVentaMapper;
    private final SucursalMapper sucursalMapper;

    public Venta requestEntidad(VentaRequest request, Sucursales sucursales, List<DetalleVenta> detalleVentas) {
        if (request == null) return null;
        return Venta.builder()
                .sucursales(sucursales)
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .detalleVentas(detalleVentas)
                .build();
    }

    public VentaResponse entidadResponse(Venta venta) {
        if (venta == null) return null;

        List<DetalleVentaResponse> detalleVentaResponses = venta.getDetalleVentas() == null
                ? List.of()
                : venta.getDetalleVentas().stream()
                .map(detalleVentaMapper::entidadResponse)
                .toList();

        // Calcular total sumando los subtotales de cada detalle
        BigDecimal total = detalleVentaResponses.stream()
                .map(DetalleVentaResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new VentaResponse(
                venta.getId(),
                venta.getFecha() != null ? venta.getFecha().toString() : null,
                venta.getEstadoVenta() != null ? venta.getEstadoVenta().getDescripcion() : null,
                sucursalMapper.entidadResponse(venta.getSucursales()),
                detalleVentaResponses,
                total
        );
    }
    /*
    *
    * */
    public  List<ReporteVentasResponse> projectionAReponse(List<ReporteVentaProjection> resultados) {
        if (resultados == null) return null;

        return resultados.stream().map(res -> new ReporteVentasResponse(
                        res.getIdSucursal(), res.getNombreSucursal(),
                        res.getPrecioProducto(), res.getCantidadProductosVendidos()))
                .toList();
    }
}