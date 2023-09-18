package ipc2.bank.database;

import java.sql.Connection;
import java.util.Optional;
import ipc2.bank.models.Cuenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yenni
 */
public class CuentaDB {

    private Connection connection;

    public CuentaDB(Connection connection) {
        this.connection = connection;
    }

    public Optional<Cuenta> getCuenta(int idCuenta) {
        Cuenta cuenta = null;
        String query = "SELECT * FROM cuenta WHERE codigo = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idCuenta);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    cuenta = new Cuenta(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return Optional.ofNullable(cuenta);
    }

    public void updateSaldo(Float nuevoSaldo, int idCuenta) throws SQLException {
        String query = "UPDATE cuenta SET saldo = ? WHERE codigo = ?";
        PreparedStatement update = connection.prepareStatement(query);
        update.setFloat(1, nuevoSaldo);
        update.setInt(2, idCuenta);
        update.executeUpdate();
        System.out.println("Updated de una cuenta / monto exitoso");
    }

}