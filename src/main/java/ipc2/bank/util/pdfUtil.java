package ipc2.bank.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yenni
 */
public class PdfUtil {

    private Connection connection;

    public PdfUtil(Connection connection) {
        this.connection = connection;
    }

    public Optional<Blob> getBlob(HttpServletRequest request, String campoForm) {
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
            System.out.println("error al crear un blob " + e);
        }
        return Optional.ofNullable(blob);
    }

    public void showDPI(HttpServletRequest request, HttpServletResponse response)
            throws IOException, NumberFormatException, SQLException {
        // Recuperar el contenido del archivo PDF del BLOB en la base de datos
        byte[] pdfContent = getFromDB(Integer.parseInt(request.getParameter("idUserInput")));
        // Establecer el tamaño del contenido en el encabezado de respuesta
        response.setContentLength(pdfContent.length);

        // Obtener OutputStream de la respuesta
        OutputStream outputStream = response.getOutputStream();

        // Escribir el contenido del archivo PDF en el OutputStream
        //outputStream.write(pdfContent);
         outputStream.write(new String(pdfContent, "ISO-8859-1").getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private byte[] getFromDB(int codigoUsuario) throws SQLException, IOException {
        String query = "SELECT pdfDPI FROM cliente WHERE codigoUsuario = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, codigoUsuario);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            Blob blob = resultSet.getBlob(1);
            byte[] blobBytes = new byte[(int) blob.length()];
            InputStream inputStream = blob.getBinaryStream();
            inputStream.read(blobBytes);
            return blobBytes;
        }
        return null;
    }
}
