
package ipc2.bank.models;

import ipc2.bank.database.LimiteDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@AllArgsConstructor @Getter @Setter
public class Limite {
    private int id;
    private float value;
    private String name;

    public Limite(int id, float value) {
        this.id = id;
        this.value = value;
    }
    
    public Limite(ResultSet rs) throws SQLException{
        id = rs.getInt("id");
        value = rs.getFloat("valor");
        name = rs.getString("nombre");
    }
    
    public boolean isValid(LimiteDB limDB){
        boolean valid = false;
        if (this.getValue() >= 0) {
            Limite other;
            switch (this.getId()) {
                case 1:
                    other = limDB.getLimit(2).get();
                    if(this.getValue()<other.getValue()){
                        valid = true;
                    }
                    break;
                case 2:
                    other = limDB.getLimit(1).get();
                    if(this.getValue()>other.getValue()){
                        valid = true;
                    }
                    break;
                default:
                    valid = false;
            }
        }
        return valid;
    }
}
