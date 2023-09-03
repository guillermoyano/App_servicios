package com.proyecto.EquipoUno.repositorios;

import com.proyecto.EquipoUno.entidad.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guille
 */
@Repository
public interface AdministradorRepositorio extends JpaRepository <Administrador, Integer>{
    
   @Query("SELECT a FROM Administrador a WHERE a.nombre = :nombre")
    public Administrador buscarPorNombre(@Param("nombre") String nombre);
    
}
