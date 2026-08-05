package com.efrain.almacen.dto.ventas;

import java.math.BigDecimal;

public record DetalleVentaResponse(
        Long idProducto,
        String nombreProducto,
        Integer cantidadProdcuto,
        BigDecimal precioProducto,
        BigDecimal subtotal
) {

}
