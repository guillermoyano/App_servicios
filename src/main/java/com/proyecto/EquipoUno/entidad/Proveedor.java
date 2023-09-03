package com.proyecto.EquipoUno.entidad;

import com.proyecto.EquipoUno.enums.Profesion;
import com.proyecto.EquipoUno.enums.Rol;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Guille
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class Proveedor extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @OneToOne
    private Imagen imagen;
    private Double calificacion;
    @Enumerated(EnumType.STRING)
    private Profesion profesion;
    private Boolean certificado;
    private String contactoTelefonico;
    private String disponibilidadHoraria;
    private Boolean alta;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToMany(mappedBy = "proveedor")
    private List<Contrato> contratos;
    
}
