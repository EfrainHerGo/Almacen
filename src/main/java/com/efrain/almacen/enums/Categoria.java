package com.efrain.almacen.enums;

import com.efrain.almacen.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Getter
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIOS("Accesosrios"),
    FARMACIA("Farmacia");

    private final String description;

    public static Categoria ObtenerCategoriaPordescripcion(String description){
        StringCustomUtils.validarNoVacio(description, "La descripcion es necesaria");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(description);

        for (Categoria categoria: values()){
            if (StringCustomUtils.quitarAcentos(categoria.description).equalsIgnoreCase(descripcionNormalizada))
                return  categoria;
        }
        throw new RecursoNoEncontradoExceptions("No existe una categoria con la descripcion" + description);
    }
}
