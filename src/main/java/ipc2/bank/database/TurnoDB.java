
package ipc2.bank.database;

import ipc2.bank.exceptions.NoConnectionFoundException;
import java.util.List;
import ipc2.bank.models.Turno;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author yenni
 */
public class TurnoDB {
    private Connection connection;

    public TurnoDB(Connection connection) {
        this.connection = connection;
    }
    public TurnoDB(HttpSession session) throws NoConnectionFoundException {
        this.connection = (Connection) session.getAttribute("conexion");
        if (this.connection == null) {
            throw new NoConnectionFoundException();
        }
    }
    
    public List<Turno> getAll(){
        String query = "SELECT * FROM turno";
        try {
            PreparedStatement select = connection.prepareStatement(query);

            List<Turno> turns = new ArrayList<>();
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                turns.add( new Turno(rs));
            }
            return turns;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public boolean validarTurno(Turno turno){
        LocalTime horaActual = LocalTime.now();
        return horaActual.isAfter(turno.getEntrada().toLocalTime()) 
                && horaActual.isBefore(turno.getSalida().toLocalTime());
    }
    
    public Optional<Turno> getTurno(int idEmpleado){
        String query = "SELECT turno.nombre, turno.horaEntrada, turno.horaSalida, turno.id "
                + "FROM empleado "
                + "JOIN turno "
                + "ON empleado.idTurno = turno.id "
                + "WHERE empleado.codigoUsuario = ?";
        Turno turno = null;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    turno = new Turno(rs.getInt("id"), 
                            rs.getString("nombre"), 
                            rs.getTime("horaEntrada"), 
                            rs.getTime("horaSalida"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(turno);
    }
    public boolean exist(int idTurn){
        String query = "SELECT * FROM turno WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idTurn);
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
