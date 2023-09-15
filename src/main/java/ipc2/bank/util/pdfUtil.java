package ipc2.bank.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author yenni
 */
public class pdfUtil {
    private Connection connection;

    public pdfUtil(Connection connection) {
        this.connection = connection;
    }
    
    public Blob getBlob(HttpServletRequest request, String campoForm) 
            throws IOException, ServletException, SQLException {
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
        Blob blob = connection.createBlob();
        blob.setBytes(1, byteArray);
        return blob;
    }
}
