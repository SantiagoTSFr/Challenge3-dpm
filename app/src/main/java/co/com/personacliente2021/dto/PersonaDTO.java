package co.com.personacliente2021.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO implements Serializable {
    private Integer idPersona;
    private Integer idTipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private boolean activo;
}