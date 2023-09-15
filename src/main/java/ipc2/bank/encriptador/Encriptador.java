
package ipc2.bank.encriptador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author yenni
 */
public class Encriptador {
    //encriptar passwords desde aqui, prueba
    /*public static void main(String[] args) {
        Encriptador encriptador = new Encriptador();
        String password = "12345678";
        System.out.println(encriptador.encriptar(password));
    }*/
    
    public String encriptar(String password) {
        try {
            // Obtener una instancia del algoritmo de hash (en este caso, MD5)
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calcular el hash de la contraseña
            md.update(password.getBytes());
            byte[] hash = md.digest();

            // Convertir el hash en una cadena hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
