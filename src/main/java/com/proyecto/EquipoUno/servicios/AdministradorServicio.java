package com.proyecto.EquipoUno.servicios;

import com.proyecto.EquipoUno.entidad.Administrador;
import com.proyecto.EquipoUno.enums.Rol;
import com.proyecto.EquipoUno.excepciones.MiException;
import com.proyecto.EquipoUno.repositorios.AdministradorRepositorio;
import com.proyecto.EquipoUno.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Guille
 */
@Service
public class AdministradorServicio implements UserDetailsService {

    @Autowired
    private AdministradorRepositorio AdministradorRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void Registrar(MultipartFile archivo, String nombre, String apellido, String correo, String documento, Boolean registro, String password, String password2) throws  MiException { //throws MiExcepcion 

        validar(nombre, apellido, correo, documento, password, password2);

        Administrador administrador = new Administrador();

        administrador.setNombre(nombre);
        administrador.setApellido(apellido);
        administrador.setEmail(correo);
        administrador.setDocumento(documento);
        administrador.setRegistro(Boolean.FALSE);

        administrador.setPassword(new BCryptPasswordEncoder().encode(password));
        administrador.setRol(Rol.ADMIN);
        //Imagen imagen = imagenServicio.guardar(archivo);

        //administrador.setImagen(imagen);

        AdministradorRepositorio.save(administrador);
    }

    private void validar(String nombre, String apellido, String correo, String documento, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("Debe ingresar nombre");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("Debe ingresar apellido");
        }
        if (correo.isEmpty() || correo == null) {
            throw new MiException("Debe ingresar correo");
        }
        if (documento.isEmpty() || documento == null) {
            throw new MiException("Debe ingresar documento");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("Debe ingrear un password y de mas de 5 caracteres");
        }

        if (!password.equals(password2)) {
            throw new MiException("Los password ingresados deben ser iguales");
        }

    }

    @Transactional
    public void actualizar(MultipartFile archivo, Integer id, String nombre, String apellido, String correo, String documento, String password, String password2) throws  MiException {

        if (id == null ) {
            throw new MiException("Debe ingresar un id");

        }

        validar(nombre, apellido, correo, documento, password, password2);

        Optional<Administrador> respuesta = AdministradorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Administrador administrador = (Administrador) respuesta.get();

            administrador.setId(id);
            administrador.setNombre(nombre);
            administrador.setPassword(new BCryptPasswordEncoder().encode(password));
            administrador.setRol(Rol.ADMIN);

            String idImagen = null;

            if (administrador.getImagen() != null) {

                idImagen = administrador.getImagen().getId();

            }

            //Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            //administrador.setImagen(imagen);

            AdministradorRepositorio.save(administrador);

        }

    }

    public Administrador getOne(Integer id) {
        return (Administrador) AdministradorRepositorio.getOne(id);
    }

    @Transactional
    public List<Administrador> listarAdministradores() {

        List<Administrador> administradores = new ArrayList();

        administradores = AdministradorRepositorio.findAll();

        return administradores;
    }

    @Transactional
    public void cambiarRol(Integer id) {
        Optional<Administrador> respuesta = AdministradorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Administrador administrador = (Administrador) respuesta.get();

            if (administrador.getRol().equals(Rol.USER)) {

                administrador.setRol(Rol.ADMIN);

            } else if (administrador.getRol().equals(Rol.ADMIN)) {

                administrador.setRol(Rol.USER);

            }

        }
    
}

@Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
