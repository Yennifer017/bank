package ipc2.bank.database;

import static ipc2.bank.database.UserDB.ID_BANCA_VIRTUAL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import ipc2.bank.models.RegistroCambio;
import ipc2.bank.models.User;
import java.util.ArrayList;
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
            LocalDate today = LocalDate.now();

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

    public List<RegistroCambio> getReport(int typeUser) {
        String query = "SELECT cambioRealizado.codUsuarioModificado, fecha, hora, nombre, "
                + "direccion, noIdentificacion FROM cambioRealizado "
                + "JOIN usuario "
                + "ON cambioRealizado.codUsuarioModificado = usuario.codigo "
                + "WHERE usuario.tipoUsuario = 3";
        return null;
    }

    public List<RegistroCambio> getReportByID(int idUser) {
        String query = "SELECT cambioRealizado.fecha, cambioRealizado.hora, gerente.noIdentificacion AS identificacionGerente\n"
                + "FROM cambioRealizado "
                + "JOIN usuario cliente "
                + "ON cambioRealizado.codUsuarioModificado = cliente.codigo "
                + "JOIN usuario gerente "
                + "ON cambioRealizado.codGerente = gerente.codigo "
                + "WHERE cambioRealizado.codUsuarioModificado = ? "
                + "ORDER BY fecha";
        try {
            PreparedStatement select = connection.prepareStatement(query);

            List<RegistroCambio> cambios = new ArrayList<>();
            select.setInt(1, idUser);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                if (rs.getInt("codigo") != ID_BANCA_VIRTUAL) {
                    cambios.add(new RegistroCambio(rs));
                }
            }
            return cambios;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
