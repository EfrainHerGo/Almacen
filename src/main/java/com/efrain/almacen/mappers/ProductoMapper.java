package com.efrain.almacen.mappers;

import com.efrain.almacen.dto.productos.ProductoRequest;
import com.efrain.almacen.dto.productos.ProductoResponse;
import com.efrain.almacen.endentities.Producto;
import com.efrain.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public Producto requestEntidad(ProductoRequest request, Categoria categoria){
        if (request == null)
            return null;
        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();
    }
    public ProductoResponse entidadResponse(Producto producto){
        if (producto == null) return null;
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria().getDescription(),
                producto.getPrecio(),
                producto.getCantidad()
        );
    }
}
