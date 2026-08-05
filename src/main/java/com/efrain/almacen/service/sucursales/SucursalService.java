package com.efrain.almacen.service.sucursales;

import com.efrain.almacen.dto.sucursales.SucursalRequest;
import com.efrain.almacen.dto.sucursales.SuscursalResponse;

import java.util.List;

public interface SucursalService {
    List<SuscursalResponse> listar();
    SuscursalResponse obtenerPorId(Long id);
    SuscursalResponse registrar(SucursalRequest request);
    SuscursalResponse actualizar(SucursalRequest request, Long id);
    void eliminar(Long id);
}
