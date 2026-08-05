package com.efrain.almacen.dto.productos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

// Lo que vamos a recibir
public record ProductoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "Es nombre debe tener entre 5 y 30 caracteres")
        String nombre,

        @NotBlank(message = "Es requerido la categoria")
        String categoria,

        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,

        @Positive(message = "la cantidad debe ser positiva")
        @NotNull(message = "La cantidad es requerida")
        Integer cantidad) {
}
