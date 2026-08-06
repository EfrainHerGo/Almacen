package com.efrain.almacen.dto.ventas;

import java.math.BigDecimal;

/*
* Se crea una proyeccion en el caso de un reporte para
* "mapear" que es lo que quiere y que es lo que espera de esta
* forma se recolecta la información solicitada */
public interface ReporteVentaProjection {
    Long getIdSucursal();
    String getNombreSucursal();
    BigDecimal getPrecioProducto();
    Long getCantidadProductosVendidos();
}
