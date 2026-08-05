package com.efrain.almacen.dto.sucursales;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SuscursalResponse(
        Long id,
        String nombre,
        String direccion

) {
}
