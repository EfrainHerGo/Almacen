package com.efrain.almacen.dto.ventas;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record VentaRequest(
        @NotNull(message = "El ID de la sucursal es requerido")
        @Positive(message = "El ID de la sucursal debe ser positivo")
        Long idSucursal,

        @NotEmpty(message = "La lista de los productos es necesario ")
        List<@Valid DetalleVentaRequest> productos
) {
}
