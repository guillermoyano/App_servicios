package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.entidad.Usuario;
import com.proyecto.EquipoUno.servicios.AdministradorServicio;
import com.proyecto.EquipoUno.servicios.ProveedorServicio;
import com.proyecto.EquipoUno.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Guille
 */
@Controller
@RequestMapping("/admin")
public class AdministradorControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private AdministradorServicio administradorServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "ADM_dashboard.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "ADM_listarUsuarios.html";
    }
    @GetMapping("/proveedores")
    public String listarProveedores(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "ADM_listarProveedores.html";
    }

    

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Integer id) {
        administradorServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }
}
