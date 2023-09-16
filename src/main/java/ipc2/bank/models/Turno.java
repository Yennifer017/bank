
package ipc2.bank.models;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Turno(ResultSet rs) throws SQLException{
        idType = rs.getInt("id");
        name = rs.getString("nombre");
        entrada = rs.getTime("horaEntrada");
        salida = rs.getTime("horaSalida");                
    }
    
    
}
