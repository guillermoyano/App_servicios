package com.proyecto.EquipoUno.repositorios;

import com.proyecto.EquipoUno.entidad.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guille
 */
@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, Integer> {
    
}
