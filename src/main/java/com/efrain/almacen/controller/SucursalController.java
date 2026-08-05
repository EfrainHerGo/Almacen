package com.efrain.almacen.controller;

import com.efrain.almacen.dto.sucursales.SucursalRequest;
import com.efrain.almacen.dto.sucursales.SuscursalResponse;

import com.efrain.almacen.service.sucursales.SucursalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal")
@AllArgsConstructor
public class SucursalController {
    private final SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SuscursalResponse>> listar(){
        return ResponseEntity.ok(sucursalService.listar());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<SuscursalResponse>obtenerPorId(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){

        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }
    @PostMapping
    public ResponseEntity<SuscursalResponse> registrar(
            @Valid @RequestBody SucursalRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.registrar(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SuscursalResponse> actualizar(
            @PathVariable @Positive(message = "EL ID debe ser positivo") Long id,
            @Valid @RequestBody SucursalRequest request
    ){
        return ResponseEntity.ok(sucursalService.actualizar(request, id));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El valor debe ser positivo") Long id
    ){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
