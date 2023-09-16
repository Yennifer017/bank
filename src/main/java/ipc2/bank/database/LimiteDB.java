package ipc2.bank.database;

import ipc2.bank.encriptador.Encriptador;
import ipc2.bank.models.Limite;
import ipc2.bank.models.User;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yenni
 */
public class LimiteDB {

    private Connection connection;

    public LimiteDB(Connection connection) {
        this.connection = connection;
    }

    public List<Limite> getAll() {
        String query = "SELECT * FROM limite";
        try {
            PreparedStatement select = connection.prepareStatement(query);

            List<Limite> limites = new ArrayList<>();
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                limites.add(new Limite(rs));
            }
            return limites;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public String updateIntoDB(HttpServletRequest request) {
        String error = null;
        try {
            int idLim = Integer.parseInt(request.getParameter("idParam"));
            float newValue = Float.parseFloat(request.getParameter("valueInput"));
            Limite limit = new Limite(idLim, newValue);
            if(limit.isValid(this)){
                updateLimit(limit);
            }else{
                error= "Valor del parametro introducido invalido";
            }
        } catch (NumberFormatException e) {
            error = "Formato del parametro introducido invalido";
        }
        return error;
    }

    private boolean updateLimit(Limite limit) {
        try {
            String query = "UPDATE limite SET valor = ? WHERE id = ?";
            PreparedStatement update = connection.prepareStatement(query);
            update.setFloat(1, limit.getValue());
            update.setInt(2,limit.getId());
            update.executeUpdate();
            System.out.println("Updated de un limite exitoso");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Optional<Limite> getLimit(int id) {
        Limite limite = null;
        String query = "SELECT * FROM limite WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    limite = new Limite(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(limite);
    }
}
