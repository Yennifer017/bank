
package ipc2.bank.database;

import ipc2.bank.encriptador.Encriptador;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import ipc2.bank.models.User;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yenni
 */
public class UserDB {
    private Connection connection;
    private Encriptador encriptador;
    public UserDB(Connection connection) {
        this.connection = connection;
        encriptador = new Encriptador();
        
    }
    
    public boolean autenticar(HttpServletRequest request){
        String username = request.getParameter("credentials");
        String password = request.getParameter("password");
        Optional<User> user = obtener(username, password);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return user.isPresent();
    }
    
    public Optional<User> obtener(String credentials, String password){
        User user = null;
        String query = "SELECT FROM usuario WHERE noIdentificacion = ? AND password = ?";
        String encryptedPassword = encriptador.encriptar(password);
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, credentials);
            ps.setString(2, encryptedPassword);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    //user = getData(resultSet, username, password);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(user);
    }
}
