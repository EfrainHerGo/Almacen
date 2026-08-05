package com.efrain.almacen.controller;


import com.efrain.almacen.dto.productos.ProductoRequest;
import com.efrain.almacen.dto.productos.ProductoResponse;
import com.efrain.almacen.service.productos.ProdcutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
@AllArgsConstructor
public class ProductoController {
    private final ProdcutoService prodcutoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax
    ){
        return ResponseEntity.ok(prodcutoService.listar(
                nombre, categoria, precioMin, precioMax));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ProductoResponse>obtenerPorId(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){

        return ResponseEntity.ok(prodcutoService.obtenerPorId(id));
    }
    @PostMapping
    public ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(prodcutoService.registrar(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "EL ID debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(prodcutoService.actualizar(request, id));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El valor debe ser positivo") Long id
    ){
        prodcutoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
