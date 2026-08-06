package com.efrain.almacen.repositories;

import com.efrain.almacen.dto.ventas.ReporteVentaProjection;
import com.efrain.almacen.dto.ventas.ReporteVentasResponse;
import com.efrain.almacen.endentities.Venta;
import com.efrain.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

// Tienen acceso para la consulta a la base de datos la parte de los repositorios
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);
    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);
    /*
    * El queri que se va ocupar para la generacion de reportes
    * con la union de algunas tablas
    * */
    @Query("""
           SELECT
                  s.id AS idSucursal,
                  s.nombre AS nombreSucursal,
                SUM (d.cantidadProducto * d.precioProducto) AS precioProducto,
                SUM(d.cantidadProducto) as cantidadProductosVendidos
           FROM Venta v
           JOIN v.sucursales s
           JOIN v.detalleVentas d
           WHERE v.estadoVenta = :estado
           GROUP BY s.id, s.nombre
           """)

    // En el where el nombre que espera debe ser diferente para no exista conflicto
    List<ReporteVentaProjection> obtentenerReporteVentas(@Param("estado") EstadoVenta estado);


}
