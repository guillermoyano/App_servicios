package com.proyecto.EquipoUno.repositorios;

import com.proyecto.EquipoUno.entidad.Proveedor;
import com.proyecto.EquipoUno.enums.Profesion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guille
 */
@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {


    @Query("SELECT p FROM Proveedor p WHERE p.nombre = :nombre")
    public Proveedor buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Proveedor p WHERE p.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT p FROM Proveedor p WHERE p.profesion = :profesion")
    public List<Proveedor> buscarPorProfesion(@Param("profesion") Profesion profesion);

    @Query("SELECT p, p.imagen FROM Proveedor p")
    public List<Object[]> obtenerProveedoresConImagen();

    public boolean existsByEmail(String email);

}