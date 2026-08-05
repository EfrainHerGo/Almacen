package com.efrain.almacen.service.productos;

import com.efrain.almacen.dto.productos.ProductoRequest;
import com.efrain.almacen.dto.productos.ProductoResponse;
import com.efrain.almacen.enums.Categoria;

import java.math.BigDecimal;
import java.util.List;
//Absatraccion un listado de mnetodos que va hacer el controller
public interface ProdcutoService {
    List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax);
    ProductoResponse obtenerPorId(Long id);
    ProductoResponse registrar(ProductoRequest request);
    ProductoResponse actualizar(ProductoRequest request, Long id);
    void eliminar(Long id);

}
