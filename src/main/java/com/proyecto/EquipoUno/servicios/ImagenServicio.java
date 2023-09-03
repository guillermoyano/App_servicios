package com.proyecto.EquipoUno.servicios;

import com.proyecto.EquipoUno.entidad.Imagen;
import com.proyecto.EquipoUno.entidad.Usuario;
import com.proyecto.EquipoUno.excepciones.MiException;
import com.proyecto.EquipoUno.repositorios.ImagenRepositorio;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Guille
 */
@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imgRepo;
    public Usuario Imagen;

    /// MultipartFile es el tipo de archivo en el cual se va a guardar la imagwen
    public Imagen guardar(MultipartFile archivo) throws MiException {
        /// validamos que el archivo exista
        if (archivo != null) {
            try {
                Imagen img = new Imagen();
                ///  el tipo mine lo seteampos haciendo uso del getContentTyper que proeve el MultiparFile
                //
                img.setMime(archivo.getContentType());
                /// traemos el nombre del archivo
                img.setNombre(archivo.getName());
                /// con este metodo getBytes traemos el contenido del archvio
                if (!archivo.isEmpty()) {
                    img.setContenido(archivo.getBytes());
                    /// persistimos (guardamos la imagen en el repositorio de imagenes)

                } else {
                    ClassPathResource resource = new ClassPathResource("static/img/Avatar.JPG");
                    byte[] imagenBytes = Files.readAllBytes(resource.getFile().toPath());
                    img.setContenido(imagenBytes);
                }

                return imgRepo.save(img);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }

        // Si algo sale mal o no se proporciona un archivo, retorna una entidad vacía
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {

        if (archivo != null) {
            try {
                Imagen img = new Imagen();

                if (idImagen != null) {
                    // con este opciotinal verificamos que nos traiga por repuesta una imagen del repositorio 
                    /// que concuerde con la solicitad
                    Optional<Imagen> respuesta = imgRepo.findById(idImagen);

                    if (respuesta.isPresent()) {
                        img = respuesta.get();
                    }
                }
                img.setMime(archivo.getContentType());
                img.setNombre(archivo.getName());
                img.setContenido(archivo.getBytes());
                return imgRepo.save(img);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Imagen> listarTodos() {
        return imgRepo.findAll();
    }

    
}
