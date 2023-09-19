
package ipc2.bank.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.*;

/**
 *
 * @author yenni
 */
@AllArgsConstructor @Getter @Setter
public class Cuenta {
    private int id, idCliente;
    private Date fechaCreacion;
    private float saldo;
    
    public Cuenta(ResultSet rs) throws SQLException{
        id = rs.getInt("codigo");
        idCliente = rs.getInt("codigoCliente");
        fechaCreacion = rs.getDate("fechaCreacion");
        saldo = rs.getFloat("saldo");
    }

    public Cuenta(int idCliente, Date fechaCreacion, float saldo) {
        this.idCliente = idCliente;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
    }
    
}
