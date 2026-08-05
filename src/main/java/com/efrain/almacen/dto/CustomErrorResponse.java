package com.efrain.almacen.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}
