
package ipc2.bank.models;

import ipc2.bank.util.PdfUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yenni
 */
@Getter @Setter
public class Cliente {
    private int codigoUsuario;
    private Date birth;
    private Blob pdfDPI; 

    public Cliente(int codigoUsuario, Date birth, Blob pdfDPI) {
        this.codigoUsuario = codigoUsuario;
        this.birth = birth;
        this.pdfDPI = pdfDPI;
    }
    
    public Cliente(HttpServletRequest request, int codigoUsuario, Connection connection){
        PdfUtil pdfU = new PdfUtil(connection);
        try {
            this.codigoUsuario = codigoUsuario;
            //obtener el pdf
            Optional<Blob> blob = pdfU.getBlob(request, "fileInput");
            if(blob.isPresent()){
                pdfDPI = blob.get();
            }
            //obtener fecha
            String fechaIngresada = request.getParameter("birthInput");
            birth = Date.valueOf(fechaIngresada);
           
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
    public boolean isValid(){
        try {
            return codigoUsuario != 0 && validateBirth(birth.toLocalDate()) && pdfDPI != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
    private boolean validateBirth(LocalDate date){
        LocalDate fechaActual  = LocalDate.now();
        return date.isBefore(fechaActual);
    }
}
