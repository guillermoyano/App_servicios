package com.proyecto.EquipoUno.entidad;

import com.proyecto.EquipoUno.enums.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Guille
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Administrador extends Persona {
     @OneToOne
    private Imagen imagen;
      @Enumerated(EnumType.STRING)
    private Rol rol;
    private String password;
    
}
