package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.repositorios.ContratoRepositorio;
import com.proyecto.EquipoUno.servicios.ContratoServicio;
import com.proyecto.EquipoUno.servicios.ProveedorServicio;
import com.proyecto.EquipoUno.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Guille
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {
    
    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private ContratoRepositorio contratoRepositorio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/solicitar")
    public String solicitarContrato(@RequestParam("proveedorId") Integer proveedorId, ModelMap model) {
        // Obtener datos del proveedor
        Proveedor proveedor = proveedorServicio.getOne(proveedorId);
        model.addAttribute("proveedor", proveedor);

        return "contrato_proveedor";
    }
}
