package co.com.personacliente2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento implements Serializable {
    private Integer idTipoDocumento;
    private String nombreDocumento;
}
