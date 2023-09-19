package ipc2.bank.database;

import ipc2.bank.exceptions.InsertionException;
import java.sql.Connection;
import java.util.Optional;
import ipc2.bank.models.Cuenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ipc2.bank.models.User;
import java.sql.Date;
import java.time.LocalDate;

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

    private Optional<Cuenta> getUltimaCuenta(int idCliente){
        Cuenta cuenta = null;
        String query = "SELECT * FROM cuenta WHERE codigoCliente = ? ORDER BY codigo DESC LIMIT 1";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idCliente);
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

    public List<Cuenta> getCuentas(int idUser) {
        String query = "SELECT * FROM cuenta WHERE codigoCliente = ?";
        try {
            PreparedStatement select = connection.prepareStatement(query);

            List<Cuenta> cuentasAsociadas = new ArrayList<>();
            select.setInt(1, idUser);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                cuentasAsociadas.add(new Cuenta(rs));
            }
            return cuentasAsociadas;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public int createAcount(String credentials, float saldo) throws InsertionException, SQLException{
        String error = null;
        UserDB userDB = new UserDB(connection);
        Optional<User> user =  userDB.obtener(credentials);
        connection.setAutoCommit(false);
        if(user.isPresent() && saldo>=0){
            boolean insertionOK = insertIntoDB(new Cuenta(
                    user.get().getId(), 
                    Date.valueOf(LocalDate.now()), 
                    saldo));
            if(insertionOK){
                Optional<Cuenta> cuenta = this.getUltimaCuenta(user.get().getId());
                if(cuenta.isPresent()){
                    connection.commit();
                    return cuenta.get().getId();
                }else{
                    error = "Error inesperado, no se ha podido obtener el codigo de cuenta creada";
                }
            }else{
                error = "Error inesperado en la base de datos";
            }
        }else if(user.isEmpty()){
            error = "Credenciales de usuario no encontradas en la base de datos";
        }else if(saldo<0){
            error = "El saldo no puede ser negativo";
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new InsertionException(error);
    }
    
    public boolean insertIntoDB(Cuenta cuenta){
        String query = "INSERT INTO cuenta(codigoCliente, fechaCreacion, saldo) VALUES(?, ?, ?)";
        try {
            PreparedStatement insert = connection.prepareStatement(query);
            insert.setInt(1, cuenta.getIdCliente());
            insert.setDate(2, cuenta.getFechaCreacion());
            insert.setFloat(3, cuenta.getSaldo());
            insert.executeUpdate();
            System.out.println("cuenta creada exitosamente");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    
}
