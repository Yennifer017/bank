
package ipc2.bank.database;

import ipc2.bank.encriptador.Encriptador;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ipc2.bank.models.Cliente;
import ipc2.bank.models.Empleado;
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
            if(userDB.insertIntoDB(user)){
                User usuario = userDB.obtener(user.getNoIdentificacion(), user.getPassword()).get();
                if(insertIntoDB(new Cliente(request,usuario.getId(), this.connection))){
                    connection.commit(); //para guardar cambios
                }else{
                    error = "La fecha de nacimiento o el pdf no se ha ingresado mal";
                }
            }else{
                error = "Algun dato de usuario esta incorrecto o su no de identificacion esta repetido.";
            }
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDB.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return error;
    }
    
    
    private boolean insertIntoDB(Cliente cliente){
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
    
    public String updateIntoDB(HttpServletRequest request, int idUser){
        String error = null;
        try {
            connection.setAutoCommit(false); //empieza una transaccion
            UserDB userDB = new UserDB(connection);
            User user = new User(request, UserDB.CLIENTE); //crear el user con los datos del forms
            user.setId(idUser);
            if (user.coregir(userDB.obtener(idUser).get())) {
                if (user.isValid()) {
                    userDB.updateIntoDB(user);
 
                    Cliente cliente = new Cliente(request, idUser, connection);
                    //GUARDAR EL CLIENTE
                    error = this.updateOptionalCamps(cliente);
                    RegistroCambioDB regDB = new RegistroCambioDB(connection);
                    User currentGerente = (User) request.getSession().getAttribute("user");
                    if(regDB.guardarRegistro(currentGerente.getId() , idUser)&& error == null){
                        connection.commit();
                    }else{
                        error += "El registro no se guardo correctamente, no se pudo actualizar";
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
            error = "error inesperado";
            Logger.getLogger(ClienteDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }
    
    private boolean preExist(int codigoUsuario){
        String query = "SELECT codigoUsuario FROM cliente WHERE codigoUsuario = ?";
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
    
    /**
     * ACTUALIZA LOS CAMPOS QUE SEAN VALIDOS DE UN CLIENTE
     * @param cliente cliente a actualizar
     */
    public String updateOptionalCamps(Cliente cliente){
        String error = "";
        try {
            if(cliente.getBirth()!= null){
                error += updateBirth(cliente)?"":"Input de birth invalido";
            }
            
            if(cliente.getPdfDPI()!=null){
                error += updatePdf(cliente)?"":"Input de pdf invalido";
            }

        } catch (Exception e) {
            error = e.toString();
        }
        return error.isEmpty()?null:error;
    }
    private boolean updateBirth(Cliente cliente){
        try {
            String query = "UPDATE cliente SET birth = ? WHERE codigoUsuario = ?";
            PreparedStatement update = connection.prepareStatement(query);
            update.setDate(1, cliente.getBirth());
            update.setInt(2, cliente.getCodigoUsuario());
            update.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    private boolean updatePdf(Cliente cliente){
        try {
            String query = "UPDATE cliente SET pdfDPI = ? WHERE codigoUsuario = ?";
            PreparedStatement update = connection.prepareStatement(query);
            update.setBlob(1, cliente.getPdfDPI());
            update.setInt(2, cliente.getCodigoUsuario());
            update.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
}
