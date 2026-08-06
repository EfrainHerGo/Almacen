package com.efrain.almacen.service.venta;

import com.efrain.almacen.dto.ventas.ReporteVentasResponse;
import com.efrain.almacen.dto.ventas.VentaRequest;
import com.efrain.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listarActivas();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);

    List<ReporteVentasResponse> reporte();
}

