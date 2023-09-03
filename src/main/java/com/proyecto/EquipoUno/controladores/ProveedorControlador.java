package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Imagen;
import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.enums.Profesion;
import com.proyecto.EquipoUno.servicios.ProveedorServicio;
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
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    ProveedorServicio proveedorServicio;

    @GetMapping("/lista/{profesion}")
    public String listarProveedoresPorProfesion(ModelMap model, @PathVariable String profesion) {
        Profesion profesionEnum = Profesion.valueOf(profesion.toUpperCase());

        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorProfesion(profesionEnum);
        model.addAttribute("proveedores", proveedores);

        return "proveedor_list.html";

    }

    @GetMapping("/lista")
    public String listarProveedores(ModelMap model, @RequestParam String email) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        model.addAttribute("proveedores", proveedores);
        return "proveedor_list.html";
    }
    
    //debe ir en modificar en ADM_listarProveedores
    @GetMapping("/darBaja/{id}")
    public String darBaja(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            proveedorServicio.darBaja(id);

            redirectAttributes.addFlashAttribute("exito", "El proveedor ha sido de baja correctamente.");

            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/proveedores";
        }

    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorServicio.getOne(id);
            Imagen imagen = proveedor.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen.getContenido(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('PROVEEDOR')")
    @GetMapping("/modificarPerfil/{id}")
    public String modificarPerfil(@PathVariable Integer id, ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        if (proveedor == null || !id.equals(proveedor.getId())) {
            return "redirect:/inicio";
        } else {
            modelo.put("proveedor", proveedor);
        }
        return "PROV_modificar_perfil.html";
    }
    
    
    //debe ir en modificar en ADM_listarProveedores
    @GetMapping("/modificarPerfilAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificarPerfilAdmin(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("proveedor", proveedorServicio.getOne(id));
        return "PROV_modificar_perfil.html";
    }

    @PostMapping("/modificacionPerfil/{id}")
    public String modificacionPerfil(@PathVariable Integer id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String usuario, @RequestParam String documento, @RequestParam(required = false) MultipartFile archivo, @RequestParam String password, String password2 , Profesion profesion, Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception, Object{

        try {

            proveedorServicio.modificar(id, nombre, apellido, email, password, usuario, documento, archivo, profesion, certificado, contactoTelefonico, disponibilidadHoraria);

            modelo.put("exito", "Proveedor modificado con exito!");
            return "redirect:/inicio";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);

            return "redirect:/modificarPerfil/" + id;
        }

    }

}

