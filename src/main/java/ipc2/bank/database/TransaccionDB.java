package ipc2.bank.database;

import ipc2.bank.models.Cuenta;
import ipc2.bank.models.Transaccion;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * Inserta una transaccion en la base de datos, contiene todas las
     * verificaciones
     *
     * @param request para obtener inputs desde el servlet
     * @param idCajero id de cajero asociado a la transaccion
     * @param typeTrans el tipo de transaccion
     * @return un string que puede contener un error o ser null (sin errores)
     */
    public String insertIntoDB(HttpServletRequest request, int idCajero, String typeTrans) {
        String error = null;
        try {
            int idCuenta = Integer.parseInt(request.getParameter("codCuentaInput"));
            float monto = Float.parseFloat(request.getParameter("montoInput"));
            Transaccion transaccion = new Transaccion(idCuenta, idCajero, typeTrans, monto);
            connection.setAutoCommit(false); //comenzando una transaccion
            error = executeTransaction(transaccion);
            if (error == null) {
                connection.commit(); //guarda cambios
            } else {
                connection.rollback(); //eliminando todos los cambios que no son validos
            }
            connection.setAutoCommit(true); //terminando transaccion
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
            //error = "Datos ingresados invalidos" || "error inesperado de la base de datos";
            error = e.toString();
        }
        return error;
    }

    public String realizarTransferencia(HttpServletRequest request) {
        String error = null;
        try {
            int idOriginAcount = Integer.parseInt(request.getParameter("idOriginAcount"));
            int idToAcount = Integer.parseInt(request.getParameter("idToAcount"));
            float monto = Float.parseFloat(request.getParameter("montoInput"));
            if(idOriginAcount != idToAcount){
                Transaccion restaDeOriginAcount = new Transaccion(
                        idOriginAcount,
                        UserDB.ID_BANCA_VIRTUAL,
                        Transaccion.DEBITO, monto);
                Transaccion creditoToFinalAcount = new Transaccion(idToAcount,
                        UserDB.ID_BANCA_VIRTUAL,
                        Transaccion.CREDITO, monto);
                connection.setAutoCommit(false); //comenzando una transaccion
                String error1 = executeTransaction(restaDeOriginAcount);
                String error2 = executeTransaction(creditoToFinalAcount);
                if (error1 == null && error2 == null) {
                    connection.commit(); //guarda cambios
                } else {
                    error = "Datos conflictivos, no se pudo transferir.";
                    connection.rollback(); //eliminando todos los cambios que no son validos
                }
                connection.setAutoCommit(true); //terminando transaccion
            }else{
                error = "No se puede realizar una transaccion a la misma cuenta";
            }
        } catch (SQLException | NumberFormatException ex) {
            error = ex.toString();
        }
        return error;
    }

    public String executeTransaction(Transaccion transaccion) throws SQLException {
        String error = null;
        if (transaccion.isValid(connection)) {
            //connection.setAutoCommit(false); //comenzando una transaccion
            Cuenta cuentaDestino
                    = new CuentaDB(connection).getCuenta(transaccion.getIdCuentaDestino()).get();
            operateTransaction(cuentaDestino, transaccion);
            CuentaDB cuentaDB = new CuentaDB(connection);
            cuentaDB.updateSaldo(cuentaDestino.getSaldo(), cuentaDestino.getId());
            this.insertIntoDB(transaccion);
            //connection.commit(); //guarda cambios
            //connection.rollback(); //eliminando todos los cambios que no son validos
        } else {
            error = "Los datos de la transaccion son conflictivos.";
        }
        //connection.setAutoCommit(true); //terminando transaccion
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
