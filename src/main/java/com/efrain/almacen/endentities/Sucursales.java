package com.efrain.almacen.endentities;


import com.efrain.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="SUCURSAL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

public class Sucursales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")

    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;
    @Column (name = "DIRECCION")
    private String direccion;

    public void validarDatos(String nombre, String direccion){
        StringCustomUtils.validarTamanio(nombre, 5, 50, "El nombre es requerido y debe ser de 5 a 50 caracteres");
        StringCustomUtils.validarTamanio(direccion, 5, 150, "La direccion debe ser minimo de 10 caracteres y menor de 150");
        if (nombre == null)
            throw new IllegalArgumentException("El nombre es requerido");
        if (direccion == null)
            throw new IllegalArgumentException("Es requerido la direccion");
    }
    public void actualizar (String nombre, String direccion){
        validarDatos(nombre, direccion);
        this.nombre = nombre.trim();
        this.direccion = direccion;

    }
}
