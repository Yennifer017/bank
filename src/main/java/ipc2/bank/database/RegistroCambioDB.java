package ipc2.bank.database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import ipc2.bank.models.RegistroCambio;
import java.util.List;

/**
 *
 * @author yenni
 */
public class RegistroCambioDB {
    private Connection connection;
    public RegistroCambioDB(Connection connection) {
        this.connection = connection;   
    }

    
    public boolean guardarRegistro(int idGerente, int idUserMod) {
        String query = "INSERT INTO cambioRealizado(codGerente, codUsuarioModificado, fecha, hora) "
                + "VALUES (?, ?, ?, ?);";
        try {
            LocalTime horaActual = LocalTime.now();
            LocalDate  today = LocalDate.now();

            PreparedStatement insert = connection.prepareStatement(query);
            insert.setInt(1, idGerente);
            insert.setInt(2, idUserMod);
            insert.setDate(3, Date.valueOf(today));
            insert.setTime(4, Time.valueOf(horaActual));
            insert.executeUpdate();
            System.out.println("registro guardado correctamente");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    public List<RegistroCambio> getReport(int typeUser){
        String query = "SELECT cambioRealizado.codUsuarioModificado, fecha, hora, nombre, "
                + "direccion, noIdentificacion FROM cambioRealizado "
                + "JOIN usuario "
                + "ON cambioRealizado.codUsuarioModificado = usuario.codigo "
                + "WHERE usuario.tipoUsuario = 3";
        return null;
    }
    public List<RegistroCambio> getReportByID(int idUser){
        String query = "SELECT cambioRealizado.codUsuarioModificado, fecha, hora, nombre, direccion, "
                + "noIdentificacion FROM cambioRealizado "
                + "JOIN usuario "
                + "ON cambioRealizado.codUsuarioModificado = usuario.codigo "
                + "WHERE cambioRealizado.codUsuarioModificado = 2";
        return null;
    }
}
