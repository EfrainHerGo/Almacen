package com.efrain.almacen.service.venta;

import com.efrain.almacen.dto.ventas.VentaRequest;
import com.efrain.almacen.dto.ventas.VentaResponse;
import com.efrain.almacen.endentities.Venta;
import com.efrain.almacen.mappers.VentaMapper;
import com.efrain.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class VentaImpl implements VentaService {
    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    @Override
    public List<VentaResponse> listarActivas() {
        Strign
        return List.of();
    }

    @Override
    public List<VentaResponse> listarCanceladas() {
        return List.of();
    }

    @Override
    public VentaResponse obtenerPorIdActiva(Long id) {
        return null;
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        return null;
    }

    @Override
    public VentaResponse cancelar(Long id) {
        return null;
    }
    public
}
