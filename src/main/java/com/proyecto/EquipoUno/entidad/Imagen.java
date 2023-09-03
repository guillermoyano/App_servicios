package com.proyecto.EquipoUno.entidad;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Guille
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    /// este atributo agfrega el formato del tamaño de la Imagen 
    private String mime;
    
    private String nombre;
    
    // la forma en que se va a guardar el contenido
    // @lob le informamos a netbeans que el archivo puede ser grande en cantida de megas 
    // QBasic con esto le iformamos que el tipo de carga va a ser perezossa o leassy para que
    /// este archivo se cargue solo cuando lo pidamos hciendo que las Query sean mas livianas
    
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
    
}
