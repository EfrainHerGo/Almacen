package com.efrain.almacen.controller;

import com.efrain.almacen.dto.ventas.ReporteVentasResponse;
import com.efrain.almacen.dto.ventas.VentaRequest;
import com.efrain.almacen.dto.ventas.VentaResponse;
import com.efrain.almacen.service.venta.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/activas")
    public ResponseEntity<List<VentaResponse>> listarActivas() {
        return ResponseEntity.ok(ventaService.listarActivas());
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas() {
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @GetMapping("/activa/{id}")
    public ResponseEntity<VentaResponse> obtenerPorIdActiva(@PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@Valid @RequestBody VentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<VentaResponse> cancelar(@PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(ventaService.cancelar(id));
    }
    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteVentasResponse>> reporte(
    ){
        return ResponseEntity.ok(ventaService.reporte());
    }
}