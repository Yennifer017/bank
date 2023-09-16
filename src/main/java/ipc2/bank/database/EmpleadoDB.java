package ipc2.bank.database;

import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.Empleado;
import ipc2.bank.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yenni
 */
public class EmpleadoDB {

    private Connection connection;

    public EmpleadoDB(Connection connection) {
        this.connection = connection;
    }
    public EmpleadoDB(HttpSession session) throws NoConnectionFoundException {
        this.connection = (Connection) session.getAttribute("conexion");
        if (this.connection == null) {
            throw new NoConnectionFoundException();
        }
    }

    public String updateIntoDB(HttpServletRequest request, int typeUser, int idUser) {
        String error = null;
        try {
            connection.setAutoCommit(false); //empieza una transaccion
            UserDB userDB = new UserDB(connection);
            User user = new User(request, typeUser); //crear el user con los datos del forms
            user.setId(idUser);
            if (user.coregir(userDB.obtener(idUser).get())) {
                if (user.isValid()) {
                    userDB.updateIntoDB(user);
                    Empleado empleado = new Empleado(request, idUser);
                    if (!empleado.isValid(connection)) {
                        TurnoDB turnoDB = new TurnoDB(connection);
                        empleado.setIdTurn(turnoDB.getTurno(idUser).get().getIdType());
                    }
                    this.updateIntoDB(empleado);
                    RegistroCambioDB regDB = new RegistroCambioDB(connection);
                    User currentGerente = (User) request.getSession().getAttribute("user");
                    if(regDB.guardarRegistro(currentGerente.getId(), idUser)){
                        connection.commit();
                    }else{
                        error = "El registro no se guardo correctamente, no se pudo actualizar";
                    }
                } else {
                    error = "Algun campo no contiene informacion valida, no se pudo actualizar";
                }
            } else {
                error = "error inesperado";
            }
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            error = "error inesperado, intentalo de nuevo";
            Logger.getLogger(ClienteDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }

    public boolean updateIntoDB(Empleado empleado) {
        try {
            String query = "UPDATE empleado SET idTurno = ? WHERE codigoUsuario = ?";
            PreparedStatement update = connection.prepareStatement(query);
            update.setInt(1, empleado.getIdTurn());
            update.setInt(2, empleado.getIdUser());
            update.executeUpdate();
            System.out.println("Updated de un empleado exitoso");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
