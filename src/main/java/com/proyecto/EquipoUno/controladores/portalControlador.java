package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.entidad.Usuario;
import com.proyecto.EquipoUno.enums.Profesion;
import com.proyecto.EquipoUno.enums.Rol;
import com.proyecto.EquipoUno.repositorios.AdministradorRepositorio;
import com.proyecto.EquipoUno.repositorios.UsuarioRepositorio;
import com.proyecto.EquipoUno.servicios.ProveedorServicio;
import com.proyecto.EquipoUno.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Guille
 */
@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String index(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedorlogueado = (Proveedor) session.getAttribute("proveedorsession");

        if (logueado != null && logueado.getRol() != null && logueado.getRol().toString().equals("ADMIN")) {
            return "ADM_dashboard.html";
        } else if (proveedorlogueado != null && proveedorlogueado.getRol() != null && proveedorlogueado.getRol().toString().equals("PROVEEDOR")) {
            return "PROV_panelProveedor.html";
        } else {
            return "inicio.html";
        }
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrarUsuario")
    public String registrar() {
        return "registroUsuario.html";
    }

    @PostMapping("/registroUsuario")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento, MultipartFile archivo, @RequestParam String password, String password2, @RequestParam Rol rol, String usuario, Double calificacion,
            Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception {
        try {
            usuarioServicio.crearUsuario(nombre, apellido, usuario, documento, archivo, email, password, contactoTelefonico, disponibilidadHoraria);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroUsuario";
        }
        modelo.put("exito", "has sido registrado con exito!");
        return "index.html";
    }

    @GetMapping("/registrarProveedor")
    public String registrarProveedor() {
        return "PROV_registroProveedor";
    }

    

    @PostMapping("/registroProveedor")
    public String registroProveedor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento, MultipartFile archivo, @RequestParam String password, String password2, @RequestParam Rol rol, String usuario, Double calificacion, Profesion profesion,
            Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception {
        try {

            proveedorServicio.registrar(nombre, apellido, email, password, usuario, documento, archivo, profesion, certificado, contactoTelefonico, disponibilidadHoraria);

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);

            return "PROV_registroProveedor";
        }
        modelo.put("exito", "has sido registrado con exito!");
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseñas invalidos");

        }
        return "login.html";
    }

}
