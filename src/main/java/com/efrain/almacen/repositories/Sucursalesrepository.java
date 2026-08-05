package com.efrain.almacen.repositories;

import com.efrain.almacen.endentities.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Sucursalesrepository extends JpaRepository<Sucursales, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
