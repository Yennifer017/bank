package ipc2.bank.models;

import ipc2.bank.util.Funcionalidad;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter
@Setter
public class User {

    private int id, tipoUsuario;
    private String name, address, noIdentificacion, sexo, password;
    private Funcionalidad[] functions;

    public User(int id, int tipoUsuario, String name, String address, String noIdentificacion, String sexo) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.name = name;
        this.address = address;
        this.noIdentificacion = noIdentificacion;
        this.sexo = sexo;
    }

    public User(ResultSet rs) throws SQLException {
        id = rs.getInt("codigo");
        tipoUsuario = rs.getInt("tipoUsuario");
        name = rs.getString("nombre");
        address = rs.getString("direccion");
        noIdentificacion = rs.getString("noIdentificacion");
        sexo = rs.getString("sexo");
        password = rs.getString("password");
    }

    /**
     * Obtiene todos los datos necesarios del request para setear los datos del
     * usuario
     *
     * @param request para obtener los datos del fronted
     * @param tipoUsuario para saber el tipo de usuario que se guardara
     */
    public User(HttpServletRequest request, int tipoUsuario) {
        password = request.getParameter("passwordInput");
        noIdentificacion = request.getParameter("noIdentificacionInput");
        name = request.getParameter("nameInput");
        address = request.getParameter("addressInput");
        this.tipoUsuario = tipoUsuario;
        try {
            //convertir
            int sexInput = Integer.parseInt(request.getParameter("sexInput"));
            switch (sexInput) {
                case 1:
                    sexo = "masculino";
                    break;
                case 2:
                    sexo = "femenino";
                    break;
            }
        } catch (NullPointerException | NumberFormatException e) {
            sexo = "";
            System.out.println(e);
        }
    }

    /**
     * corrige el usuario creado de tal manera que se vuelva valido,
     *
     * @param originalUserFromDB el user de modelo para corregir el usuario
     * @return true, si se corrigio correctamente, false de lo contrario
     */
    public boolean coregir(User originalUserFromDB) {
        if (originalUserFromDB.isValid()) {
            try {
                name = presentValue(name) ? name : originalUserFromDB.name;
                address = presentValue(address) ? address : originalUserFromDB.address;
                sexo = presentValue(sexo) ? sexo : originalUserFromDB.getSexo();
                tipoUsuario = (tipoUsuario != 0) ? tipoUsuario : originalUserFromDB.getTipoUsuario();
                noIdentificacion = presentValue(noIdentificacion)
                        ? noIdentificacion : originalUserFromDB.getNoIdentificacion();
            } catch (NullPointerException e) {
                return false;
            }
        }
        return originalUserFromDB.isValid();
    }

    public boolean isValid() {
        try {
            return presentValue(name) && presentValue(address)
                    && presentValue(noIdentificacion) && presentValue(sexo)
                    && (sexo.equals("femenino") || sexo.equals("masculino"))
                    && (tipoUsuario >= 1 && tipoUsuario <= 3)
                    && password.length() > 3;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean presentValue(String atribute) {
        return !(atribute.isBlank() && atribute.isEmpty());
    }
    

}
