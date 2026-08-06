package com.efrain.almacen.dto.ventas;

import java.math.BigDecimal;

public record ReporteVentasResponse(
        Long id,
        String nombre,
        BigDecimal totalFacturado,
        Long cantidadProductosVendidos
) {

}
