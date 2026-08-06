package com.efrain.almacen.service.productos;

import com.efrain.almacen.dto.productos.ProductoRequest;
import com.efrain.almacen.dto.productos.ProductoResponse;
import com.efrain.almacen.endentities.Producto;
import com.efrain.almacen.enums.Categoria;
import com.efrain.almacen.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.almacen.mappers.ProductoMapper;
import com.efrain.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class ProductoServiceImpl implements  ProdcutoService{
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");
        Categoria cat = null;
        if (categoria != null && !categoria.isBlank()){
            try {
                cat = obtenerCategoriaPorDescripcion(categoria);
            } catch (RecursoNoEncontradoExceptions e){
                cat = null;
            }
        }


        return productoRepository.filtrado(nombre, cat, precioMin, precioMax).stream()
                .map(productoMapper::entidadResponse).toList()
                //.map(producto -> productoMapper.entidadResponse(producto)).toList; es lo mismo
                ;

    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {

        return  productoMapper.entidadResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrado nuevo producto");
        Categoria categoria = obtenerCategoriaPorDescripcion(request.categoria());
        Producto producto = productoMapper.requestEntidad(request, categoria);
        productoRepository.save(producto);
        log.info("nuevo producto {} registrado", producto.getNombre());
        return productoMapper.entidadResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoOException(id);

        Categoria categoria = obtenerCategoriaPorDescripcion(request.categoria());

        log.info("Actualizando productos con id: {}", id);

        producto.actualizar(request.nombre(),
                categoria,
                request.precio(),
                request.cantidad());
        //productoRepository.save(producto); ya lo hace por default

        log.info("Prodcuto con id {} actualizado: ", id);
        return productoMapper.entidadResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Eliminar producto con id: {}", id);
        productoRepository.delete(producto);
        log.info("Producto {} eliminado", id);
    }
    private Producto obtenerProductoOException(Long id){
        log.info("Buscar producto con id: {}", id);
        return productoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoExceptions
                ("Prodcuto no enocntrado con id: " + id));
    }
    private Categoria obtenerCategoriaPorDescripcion(String descripcion){
        return Categoria.ObtenerCategoriaPordescripcion(descripcion.trim());
    }
}
