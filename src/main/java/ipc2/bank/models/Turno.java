
package ipc2.bank.models;

import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter @AllArgsConstructor @Setter
public class Turno {
    private int idType;
    private String name;
    private Time entrada, salida;
    
}
