package ipc2.bank.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author yenni
 */
public class PdfUtil {
    private Connection connection;

    public PdfUtil(Connection connection) {
        this.connection = connection;
    }
    
    public Optional<Blob> getBlob(HttpServletRequest request, String campoForm){
        Blob blob = null;
        try {
            // Obtener el archivo PDF ingresado desde el formulario web
            Part filePart = request.getPart(campoForm); // "archivoPDF" es el nombre del campo de formulario para subir el archivo

            // Crear un flujo de entrada para leer el archivo PDF
            InputStream fileContent = filePart.getInputStream();

            //lee los bytes del input stream y los carga en un arreglo de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] byteArray = outputStream.toByteArray();

            //crea el objeto blob usando el arreglo de bytes
            blob = connection.createBlob();
            blob.setBytes(1, byteArray);
        } catch (Exception e) {
            System.out.println("error al crear un blob " +  e);
        }
        return Optional.ofNullable(blob);
    }
}
