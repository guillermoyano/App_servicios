package com.proyecto.EquipoUno.servicios;

import com.proyecto.EquipoUno.entidad.Contrato;
import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.entidad.Usuario;
import com.proyecto.EquipoUno.repositorios.ContratoRepositorio;
import com.proyecto.EquipoUno.repositorios.ProveedorRepositorio;
import com.proyecto.EquipoUno.repositorios.UsuarioRepositorio;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guille
 */
@Service
public class ContratoServicio {
    private final ContratoRepositorio contratoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProveedorRepositorio proveedorRepositorio;

    public ContratoServicio(ContratoRepositorio contratoRepositorio, UsuarioRepositorio usuarioRepositorio, ProveedorRepositorio proveedorRepositorio) {
        this.contratoRepositorio = contratoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }

    public Contrato solicitarContrato(Integer usuarioId, Integer proveedorId, String descripcion, String direccionDelContrato) {
        Usuario usuario = usuarioRepositorio.findById(Math.toIntExact(usuarioId))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId));

        Proveedor proveedor = proveedorRepositorio.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con el ID: " + proveedorId));

        Contrato contrato = new Contrato();
        contrato.setUsuario(usuario);
        contrato.setProveedor(proveedor);
        contrato.setDescripcionDelContrato(descripcion);
        contrato.setDireccionDelContrato(direccionDelContrato);
        contrato.setFechaDeFirma(new Date());

        return contratoRepositorio.save(contrato);
    }


}