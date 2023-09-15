
package ipc2.bank.database;

import ipc2.bank.models.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;

/**
 *
 * @author yenni
 */
public class EmpleadoDB {
    private Connection connection;

    public EmpleadoDB(Connection connection) {
        this.connection = connection;
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
    public boolean validarTurno(Turno turno){
        LocalTime horaActual = LocalTime.now();
        return horaActual.isAfter(turno.getEntrada().toLocalTime()) 
                && horaActual.isBefore(turno.getSalida().toLocalTime());
    }
}
