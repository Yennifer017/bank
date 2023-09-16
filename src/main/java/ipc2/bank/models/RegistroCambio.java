
package ipc2.bank.models;

import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yenni
 */
@AllArgsConstructor @Getter
public class RegistroCambio {
    private int id, idGerente, idUsuarioMod;
    private Date date;
    private Time hour;
}
