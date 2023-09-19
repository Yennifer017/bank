
package ipc2.bank.exceptions;

/**
 *
 * @author yenni
 */
public class InsertionException extends Exception{
    private String message;
    public InsertionException(String message){
        this.message = message;
    }
    @Override
    public String toString(){
        return message;
    }
}
