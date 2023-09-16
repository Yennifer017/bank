package ipc2.bank.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;


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
}
