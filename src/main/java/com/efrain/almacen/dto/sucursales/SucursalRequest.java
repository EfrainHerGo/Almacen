package com.efrain.almacen.dto.sucursales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min= 10, max = 50, message = "El nombre debe ser de 5 a 50 caracteres")
                        String nombre,
        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 10, max = 150, message = "La direccion debe tener mas de 10 caracteres y un maximo de 150")
        String direccion
) {

}
