package com.proyecto.EquipoUno.controladores;

import com.proyecto.EquipoUno.entidad.Imagen;
import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.servicios.ImagenServicio;
import com.proyecto.EquipoUno.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Guille
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @Autowired
    ImagenServicio usuarioServicio;

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable Integer id) {
        Proveedor proveedor = proveedorServicio.getOne(id);
        Imagen imagen = proveedor.getImagen();

        if (imagen != null) {
            byte[] contenidoImagen = imagen.getContenido();
            String tipoMime = imagen.getMime();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(tipoMime));

            return new ResponseEntity<>(contenidoImagen, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
