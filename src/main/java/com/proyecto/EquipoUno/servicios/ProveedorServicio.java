package com.proyecto.EquipoUno.servicios;

import com.proyecto.EquipoUno.entidad.Imagen;
import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.enums.Profesion;
import com.proyecto.EquipoUno.enums.Rol;
import com.proyecto.EquipoUno.excepciones.MiException;
import com.proyecto.EquipoUno.repositorios.ProveedorRepositorio;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Guille
 */
@Service
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String usuario, String documento,
            MultipartFile archivo, Profesion profesion, Boolean certificado,
            String contactoTelefonico, String disponibilidadHoraria) throws MiException, URISyntaxException {
        if (proveedorRepositorio.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado. Por favor, ingrese un email diferente.");
        }
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setEmail(email);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setUsuario(usuario);
        proveedor.setDocumento(documento);
        proveedor.setRegistro(Boolean.TRUE);
        proveedor.setCalificacion(0.0);
        proveedor.setProfesion(profesion);
        proveedor.setCertificado(certificado);
        proveedor.setContactoTelefonico(contactoTelefonico);
        proveedor.setDisponibilidadHoraria(disponibilidadHoraria);
        proveedor.setAlta(true);
        proveedor.setRol(Rol.PROVEEDOR);
        Imagen imagen = imagenServicio.guardar(archivo);
        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void modificar(Integer id, String nombre, String apellido, String correo, String password, String usuario, String documento,
            MultipartFile archivo, Profesion profesion, Boolean certificado,
            String contactoTelefonico, String disponibilidadHoraria) throws MiException {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        Proveedor proveedor = respuesta.get();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setEmail(correo);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setUsuario(usuario);
        proveedor.setDocumento(documento);
        proveedor.setRegistro(Boolean.TRUE);
        proveedor.setCalificacion(0.0);
        proveedor.setProfesion(profesion);
        proveedor.setCertificado(certificado);
        proveedor.setContactoTelefonico(contactoTelefonico);
        proveedor.setDisponibilidadHoraria(disponibilidadHoraria);
        proveedor.setAlta(true);
        proveedor.setRol(Rol.PROVEEDOR);

        if (archivo != null && !archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            proveedor.setImagen(imagen);
        }

        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void eliminarProveedor(Integer id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        Proveedor proveedor = respuesta.get();

        proveedorRepositorio.delete(proveedor);

    }

    @Transactional
    public void darBaja(Integer id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setRegistro(Boolean.FALSE);
            proveedorRepositorio.save(proveedor);
        }

    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedoresPorEmail = new ArrayList();

        proveedoresPorEmail = proveedorRepositorio.findAll();
        List<Imagen> imagenes = new ArrayList();
        imagenes = imagenServicio.listarTodos();

        return proveedoresPorEmail;
    }

    public List<Proveedor> listarProveedoresPorProfesion(Profesion profesion) {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = (List<Proveedor>) proveedorRepositorio.buscarPorProfesion(profesion);
        return proveedores;
    }

    public Proveedor getOne(Integer id) {
        return proveedorRepositorio.getOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);
        System.out.println("***************Proveedor " + proveedor.getEmail());

        if (proveedor != null && proveedor.getRegistro()) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("proveedorsession", proveedor);

            User user = new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
            return user;
        } else {
            throw new UsernameNotFoundException("Proveedor no encontrado");
        }
    }

}
