package com.efrain.almacen.dto.ventas;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Normalized;

import java.util.List;

public record VentaRequest(
        @NotNull(message = "El ID de la sucursal es requerido")
        @Positive(message = "El ID de la sucursal debe ser positivo")
        Long idSucusal,

        @NotEmpty(message = "La lista de la")
        List<@Valid DetalleVentaRequest> productos
) {
}
