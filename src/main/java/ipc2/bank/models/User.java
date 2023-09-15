package ipc2.bank.models;

import ipc2.bank.util.Funcionalidad;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter @Setter
public class User {

    private int id, tipoUsuario;
    private String name, direccion, noIdentificacion, sexo, password;
    private Funcionalidad[] functions;
    
    public User(int id, int tipoUsuario, String name, String direccion, String noIdentificacion, String sexo) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.name = name;
        this.direccion = direccion;
        this.noIdentificacion = noIdentificacion;
        this.sexo = sexo;
    }
    
    public User create(){
        return null;
    }
    
    public boolean isValid(){
        return true;
    }


}
