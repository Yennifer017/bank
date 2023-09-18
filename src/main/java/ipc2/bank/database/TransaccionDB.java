package ipc2.bank.database;

import ipc2.bank.models.Cuenta;
import ipc2.bank.models.Transaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author yenni
 */
public class TransaccionDB {

    private Connection connection;

    public TransaccionDB(Connection connection) {
        this.connection = connection;
    }
    
    public String insertIntoDB(){
        
        return null;
    }
    
    public String executeTransaction(Transaccion transaccion) throws SQLException {
        String error = null;
        if (transaccion.isValid(connection)) {
            connection.setAutoCommit(false); //comenzando una transaccion
            Cuenta cuentaDestino
                    = new CuentaDB(connection).getCuenta(transaccion.getIdCuentaDestino()).get();
            operateTransaction(cuentaDestino, transaccion); 
            CuentaDB cuentaDB = new CuentaDB(connection);
            cuentaDB.updateSaldo(cuentaDestino.getSaldo(), cuentaDestino.getId());
            this.insertIntoDB(transaccion);
            connection.commit(); //guarda cambios
            connection.rollback(); //eliminando todos los cambios que no son validos
        } else {
            error = "Los datos de la transaccion son conflictivos.";
        }
        connection.setAutoCommit(true); //terminando transaccion
        return error;
    }

    private void insertIntoDB(Transaccion transaccion) throws SQLException {
        String query = "INSERT INTO transaccion(codCuenta, tipo, fecha, hora, monto, codCajero) "
                + "VALUES (?,?,?,?,?,?)";
        PreparedStatement insert = connection.prepareStatement(query);
        insert.setInt(1, transaccion.getIdCuentaDestino());
        insert.setString(2, transaccion.getType());
        insert.setDate(3, transaccion.getFecha());
        insert.setTime(4, transaccion.getHora());
        insert.setFloat(5, transaccion.getMonto());
        insert.setInt(6, transaccion.getIdCajero());
        insert.executeUpdate();
    }

    private void operateTransaction(Cuenta cuentaDestino, Transaccion transaccion) {
        float nuevoMonto = cuentaDestino.getSaldo();
        switch (transaccion.getType()) {
            case Transaccion.CREDITO:
                nuevoMonto += transaccion.getMonto();
                break;
            case Transaccion.DEBITO:
                nuevoMonto -= transaccion.getMonto();
                break;
        }
        cuentaDestino.setSaldo(nuevoMonto);
    }

}
