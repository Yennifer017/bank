
package ipc2.bank.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import lombok.Getter;

/**
 *
 * @author yenni
 */
@Getter
public class RegistroCambio {
    private int id, idGerente, idUsuarioMod;
    private Date date;
    private Time hour;
    private String dpiGerenteAsociado;

    public RegistroCambio(int id, int idGerente, int idUsuarioMod, Date date, Time hour) {
        this.id = id;
        this.idGerente = idGerente;
        this.idUsuarioMod = idUsuarioMod;
        this.date = date;
        this.hour = hour;
    }
    
    public RegistroCambio(ResultSet rs) throws SQLException{
        date = rs.getDate("fecha");
        hour = rs.getTime("hora");
        dpiGerenteAsociado = rs.getString(3);
    }
    
    
}
