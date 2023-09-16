
package ipc2.bank.models;

import ipc2.bank.database.TurnoDB;
import ipc2.bank.database.UserDB;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Connection;
import lombok.*;


/**
 *
 * @author yenni
 */
@AllArgsConstructor @Getter @Setter
public class Empleado {
    private int idUser, idTurn;

    public Empleado(HttpServletRequest request, int idUser) {
        try {
            this.idUser = idUser;
            idTurn = Integer.parseInt(request.getParameter("idTurn"));
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    public boolean isValid(Connection connection){
        UserDB userDB = new UserDB(connection);
        TurnoDB turnoDB = new TurnoDB(connection);
        return userDB.exist(idUser) && turnoDB.exist(idTurn);
    }
    
    
}
