package com.efrain.almacen.mappers;


import com.efrain.almacen.dto.sucursales.SucursalRequest;
import com.efrain.almacen.dto.sucursales.SuscursalResponse;
import com.efrain.almacen.endentities.Sucursales;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public Sucursales requestEntidad(SucursalRequest request){
        if (request == null)
            return null;
        return Sucursales.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion())
                .build();

    }
    public SuscursalResponse entidadResponse(Sucursales sucursales){
        if (sucursales == null) return null;
        return new SuscursalResponse(
                sucursales.getId(),
                sucursales.getNombre(),
                sucursales.getDireccion()
        );
    }
}
