package co.com.personacliente2021.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Persona implements Serializable {

      private Integer idPersona;
      private String numeroDocumento;
      private TipoDocumento tipoDocumento;
      private String nombre;
      private String apellido;
      private boolean activo;
}