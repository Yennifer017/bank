
package ipc2.bank.conexion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yenni
 */
public class Conexion {
    private Connection conexion;
    private final String URL = "jdbc:mysql://localhost:3306/Bank";
    private final String USER = "userforipc";
    private final String PASSWORD = "yennifer7890";
    
    public Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
            return conexion;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al registrar el driver de MySQL: " + e);
        }
        return null;
    }

    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar la conexión");
                e.printStackTrace();
            }
        }
    }
    public void closeSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
