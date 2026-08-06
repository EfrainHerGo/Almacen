package com.efrain.almacen.repositories;

import com.efrain.almacen.endentities.Producto;
import com.efrain.almacen.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
   /*De esta forma JPQL
   * Se crean consultas con parametros
   * la tabla a la que se consulta, los campos que se esperan y si pueden ser null
   * */
    @Query("""
            SELECT p 
            FROM Producto p 
            WHERE (:nombre IS NULL OR :nombre = '' 
            OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
            AND(:categoria IS NULL OR p.categoria = :categoria) 
            AND(:precioMin IS NULL OR p.precio >= :precioMin) 
            AND(:precioMax IS NULL OR p.precio <= :precioMax)
            """)
    List<Producto> filtrado (@Param("nombre") String nombre,
                             @Param("categoria")Categoria categoria,
                             @Param("precioMin")BigDecimal precioMin,
                             @Param("precioMax")BigDecimal precioMax);
}
