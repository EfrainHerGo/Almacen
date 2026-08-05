package com.efrain.almacen.dto.productos;


import com.efrain.almacen.utils.StringCustomUtils;

import java.math.BigDecimal;

//Respuesta que vanos a devolver
public record ProductoResponse(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer cantidad) {
}
