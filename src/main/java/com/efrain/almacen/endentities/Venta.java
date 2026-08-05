package com.efrain.almacen.endentities;


import com.efrain.almacen.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTAS")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private String fecha;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursales sucursales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta",
    orphanRemoval = true, cascade = CascadeType.ALL) // Para que se elimine los hijos si se eliminan el padre
    @Builder.Default //para asignar el valor de una lista vacia en ves de nullo
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public  void agregadDetalle(DetalleVenta detalleVenta){
        if(detalleVenta == null)
            throw new IllegalArgumentException("El detalle de la venta es requerido");
        if (this.detalleVentas == null)
            this.detalleVentas = new ArrayList<>();
        this.detalleVentas.add(detalleVenta);
    }
    public void cancelar(){
        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new IllegalArgumentException("La venta ya esta cancelada");

        this.estadoVenta = EstadoVenta.CANCELADA;
    }
}
