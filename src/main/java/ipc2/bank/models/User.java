package ipc2.bank.models;

import ipc2.bank.util.Funcionalidad;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * Obtiene todos los datos necesarios del request para setear los datos del
     * usuario
     *
     * @param request para obtener los datos del fronted
     * @param tipoUsuario para saber el tipo de usuario que se guardara
     */
    public User(HttpServletRequest request, int tipoUsuario){
        try {
            password = request.getParameter("passwordInput");
            noIdentificacion = request.getParameter("noIdentificacionInput");
            name = request.getParameter("nameInput");
            address = request.getParameter("addressInput");
            //convertir
            int sexInput = Integer.parseInt(request.getParameter("sexInput"));
            switch (sexInput) {
                case 1:
                    sexo = "Masculino";
                    break;
                case 2:
                    sexo = "Femenino";
                    break;
            }
            this.tipoUsuario = tipoUsuario;
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println(e);
        }
    }


    public boolean isValid() {
        try {
            return presentValue(name) && presentValue(address)
                && presentValue(noIdentificacion) && presentValue(sexo)
                && (sexo.equals("Femenino") || sexo.equals("Masculino"))
                && (tipoUsuario >= 1 && tipoUsuario <= 3)
                && password.length()>3;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean presentValue(String atribute) {
        return !(atribute.isBlank() && atribute.isEmpty());
    }

}
