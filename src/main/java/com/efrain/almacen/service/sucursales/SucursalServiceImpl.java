package com.efrain.almacen.service.sucursales;

import com.efrain.almacen.dto.sucursales.SucursalRequest;
import com.efrain.almacen.dto.sucursales.SuscursalResponse;
import com.efrain.almacen.endentities.Sucursales;
import com.efrain.almacen.exceptions.RecursoNoEncontradoExceptions;
import com.efrain.almacen.mappers.SucursalMapper;
import com.efrain.almacen.repositories.Sucursalesrepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService{
    private final Sucursalesrepository sucursalesrepository;
    private final SucursalMapper sucursalMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SuscursalResponse> listar() {
        log.info("Listando todas las sucursales");
        return sucursalesrepository.findAll().stream()
                .map(sucursalMapper::entidadResponse).toList();
    }

    @Override
    public SuscursalResponse obtenerPorId(Long id) {
        return sucursalMapper
                .entidadResponse(obtenerSucursalOException(id));
    }

    @Override
    public SuscursalResponse registrar(SucursalRequest request) {
        log.info("Registrar sucursal nueva");
        validarDatosUnicos(request);
        Sucursales sucursales = sucursalMapper.requestEntidad(request);
        sucursalesrepository.save(sucursales);
        log.info("Nueva sucursal {} registrado", sucursales.getNombre());
        return sucursalMapper.entidadResponse(sucursales);
    }

    @Override
    public SuscursalResponse actualizar(SucursalRequest request, Long id) {
        Sucursales sucursales = obtenerSucursalOException(id);
        log.info("Actualizando sucursal con id: " + id);
        validarCambiosUnicos(request, id);
        sucursales.actualizar(request.nombre(), request.direccion());
        log.info("Sucursal con id: {} actualizado", id);

        return sucursalMapper.entidadResponse(sucursales);
    }

    @Override
    public void eliminar(Long id) {
        Sucursales sucursales = obtenerSucursalOException(id);
        log.info(("Eliminar sucursal con id: " + id ));
        sucursalesrepository.delete(sucursales);
        log.info("Sucursal {} eliminado ", id);

    }
    private Sucursales obtenerSucursalOException(Long id){
        log.info("Buscar sucursal con id: {}", id);
        return sucursalesrepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoExceptions
                ("Prodcuto no enocntrado con id: " + id));
    }
    private void validarDatosUnicos(SucursalRequest request){
        log.info("Validar nombres unicos");
        if (sucursalesrepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya eciste una sucursal con el nombre de: " + request.nombre());
    }
    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validar nombres unicos");
        if (sucursalesrepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya eciste una sucursal con el nombre de: " + request.nombre());
    }
}
