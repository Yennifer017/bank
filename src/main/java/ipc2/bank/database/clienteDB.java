
package ipc2.bank.database;

import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ipc2.bank.models.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author yenni
 */
public class ClienteDB {
    private Connection connection;

    public ClienteDB(Connection connection) {
        this.connection = connection;
    }
    
    public ClienteDB(HttpSession session) throws NoConnectionFoundException {
        this.connection = (Connection) session.getAttribute("conexion");
        if (this.connection == null) {
            throw new NoConnectionFoundException();
        }
    }
    
    public String InsertIntoDB(HttpServletRequest request){
        String error = null;
        try {
            //comenzando una transaccion
            connection.setAutoCommit(false);
            
            UserDB userDB = new UserDB(connection);
            User user = new User(request, 1);
            if(userDB.InsertIntoDB(user)){
                User usuario = userDB.obtener(user.getNoIdentificacion(), user.getPassword()).get();
                if(InsertIntoDB(new Cliente(request,usuario.getId(), this.connection))){
                    connection.commit(); //para guardar cambios
                }else{
                    error = "La fecha de nacimiento o el pdf no se ha ingresado mal";
                }
            }else{
                error = "Algun dato de usuario esta incorrecto o su no de identificacion esta repetido.";
            }
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDB.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return error;
    }
    
    
    private boolean InsertIntoDB(Cliente cliente){
        if(cliente.isValid() && !preExist(cliente.getCodigoUsuario())){
            String query = "INSERT INTO cliente(codigoUsuario, birth, pdfDPI) VALUES (?,?,?)";
            try {
                PreparedStatement insert = connection.prepareStatement(query);
                insert.setInt(1, cliente.getCodigoUsuario());
                insert.setDate(2, cliente.getBirth());
                insert.setBlob(3, cliente.getPdfDPI());
                insert.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }else{
            return false;
        }
    }
    
    private boolean preExist(int codigoUsuario){
        String query = "SELECT * FROM cliente WHERE codigoUsuario = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, codigoUsuario);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
            return false;
        }
    }
    
}
