package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Imagen;
import com.proyecto.EquipoUno.entidad.Usuario;
import com.proyecto.EquipoUno.enums.Rol;
import com.proyecto.EquipoUno.repositorios.UsuarioRepositorio;
import com.proyecto.EquipoUno.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modificarPerfil/{id}")
    public String modificarPerfil(@PathVariable Integer id, ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario == null || !id.equals(usuario.getId())) {
            return "inicio.html";
        } else {
            modelo.put("usuario", usuario);
        }
        return "modificar_perfil_usuario.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modificarPerfilAdmin/{id}")
    public String modificarPerfilAdmin(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("usuario", usuarioRepositorio.getOne(id));
        return "modificar_perfil_usuario.html";

    }

    @PostMapping("/modificacionPerfil/{id}")
    public String modificacionPerfil(@PathVariable Integer id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String usuario, @RequestParam String documento, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam (required=false) String password,  @RequestParam (required=false) String password2, @RequestParam Rol rol, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception{

        try {
            usuarioServicio.modificarUsuario(id, nombre, apellido, usuario, documento, archivo, email, password, contactoTelefonico, disponibilidadHoraria);
            modelo.put("exito", "usuario modificado con exito");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);

            return "redirect:/modificarPerfil/" + id;
        }
        return "redirect:/inicio";

    }

    @GetMapping("/listarUsuarios")
    public String listarUsuarios(ModelMap modelo, @PathVariable String email) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.put("usuarios", usuarios);
        return "ADM_listarUsuarios.html";
    }

    @GetMapping("/darDeBaja/{id}")
    public String darDeBaja(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioServicio.darDeBajaUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "El usuario ha sido de baja correctamente.");
            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/listarUsuarios";
        }

    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioRepositorio.getOne(id);
            Imagen imagen = usuario.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen.getContenido(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
