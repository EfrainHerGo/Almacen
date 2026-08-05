package com.efrain.almacen.dto.ventas;

import com.efrain.almacen.dto.sucursales.SuscursalResponse;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(Long id,
                            String fecha,
                            String estado,
                            SuscursalResponse sucursal,
                            List<DetalleVentaResponse> detalles,
                            BigDecimal total) {
}
