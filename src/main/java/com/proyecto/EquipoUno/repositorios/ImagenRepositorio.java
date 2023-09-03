package com.proyecto.EquipoUno.repositorios;

import com.proyecto.EquipoUno.entidad.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guille
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
}
