package com.efrain.almacen.enums;

import com.efrain.almacen.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA (1L, "Registrada"),
    CANCELADA(0L, "Cancelada");
    private final Long codigo;
    private final String descripcion;
    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String description){
        StringCustomUtils.validarNoVacio(description, "La descripcion es necesaria");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(description);

        for (EstadoVenta estadoVenta: values()){
            if (StringCustomUtils.quitarAcentos(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return  estadoVenta;
        }
        throw new RecursoNoEncontradoExceptions("No existe una categoria con la descripcion" + description);
    }
    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        if (codigo == null)
            throw new IllegalArgumentException("el codigo es requerido y debe ser positivo o 0");
        for (EstadoVenta estadoVenta : values()){
            if (Objects.equals(estadoVenta.codigo, codigo))
                return estadoVenta;
        }
        throw new RecursoNoEncontradoExceptions("No existe estado venta con el codigo: " + codigo);
    }
}

