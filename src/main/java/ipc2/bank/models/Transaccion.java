package ipc2.bank.models;

import ipc2.bank.database.CuentaDB;
import ipc2.bank.database.UserDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter
@Setter
@NoArgsConstructor
public class Transaccion {

    private int id, idCuentaDestino, idCajero;
    private String type;
    private Date fecha;
    private Time hora;
    private float monto;

    public static final String CREDITO = "credito", DEBITO = "debito";

    public Transaccion(int idCuentaDestino, int idCajero, String type, float monto) {
        this.idCuentaDestino = idCuentaDestino;
        this.idCajero = idCajero;
        this.type = type;
        this.monto = monto;
        LocalTime horaActual = LocalTime.now();
        LocalDate today = LocalDate.now();
        hora = Time.valueOf(horaActual);
        fecha = Date.valueOf(today);
    }

    public boolean isValid(Connection connection) {
        UserDB userDB = new UserDB(connection);
        CuentaDB cuentaDB = new CuentaDB(connection);
        Optional<Cuenta> cuentaOp = cuentaDB.getCuenta(idCuentaDestino);
        boolean valid = monto > 0 && (type.equals("credito") || type.equals("debito"))
                && userDB.isType(idCajero, UserDB.CAJERO)
                && cuentaOp.isPresent();
        if (cuentaOp.isPresent()) {
            return valid && this.isPossible(type, cuentaOp.get(), monto);
        }
        return valid;
    }

    /**
     * Indica si la transaccion es posible de realizarse, ya sea acredintanto o
     * debitando
     *
     * @param typeTransaccion indica el tipo de transaccion, ya sea debito o
     * credito
     * @param cuentaDestino la cuenta a la que se desea hacer la transaccion
     * @param montoTransaccion el monto de la transaccion a realizarse
     * @return la posibilidad de transaccion
     */
    public boolean isPossible(String typeTransaccion, Cuenta cuentaDestino, float montoTransaccion)
            throws NullPointerException {

        switch (typeTransaccion) {
            case "credito":
                return true;
            case "debito":
                return (cuentaDestino.getSaldo() - montoTransaccion) >= 0;
            default:
                return false;
        }
    }
    /**
     * Indica si la transaccion con los datos seteados previamente es posible de realizarse
     * conservando la integridad de la base de datos
     * @param connection para buscar la cuenta en la base de datos
     * @return true si es posible, de lo contrario false
     */
    public boolean isPossible(Connection connection){
        Optional<Cuenta> cuentaOp = new CuentaDB(connection).getCuenta(this.idCuentaDestino);
        if(cuentaOp.isPresent()){
            return isPossible(this.type, cuentaOp.get(), monto);
        }else{
            return false;
        }
    }

}
