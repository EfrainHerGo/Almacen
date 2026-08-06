package com.efrain.almacen.mappers;

import com.efrain.almacen.dto.ventas.DetalleVentaRequest;
import com.efrain.almacen.dto.ventas.DetalleVentaResponse;
import com.efrain.almacen.endentities.DetalleVenta;
import com.efrain.almacen.endentities.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {

    public DetalleVenta requestEntidad(DetalleVentaRequest request, Producto producto) {
        if (request == null) return null;
        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(request.cantidadProducto())
                .precioProducto(producto.getPrecio())
                .build();
    }

    public DetalleVentaResponse entidadResponse(DetalleVenta detalleVenta) {
        if (detalleVenta == null) return null;

        BigDecimal subtotal = detalleVenta.getPrecioProducto()
                .multiply(BigDecimal.valueOf(detalleVenta.getCantidadProducto()));

        return new DetalleVentaResponse(
                detalleVenta.getProducto().getId(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidadProducto(),
                detalleVenta.getPrecioProducto(),
                subtotal
        );
    }
}