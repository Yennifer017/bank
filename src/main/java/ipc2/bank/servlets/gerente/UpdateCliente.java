
package ipc2.bank.servlets.gerente;

import ipc2.bank.database.ClienteDB;
import ipc2.bank.database.UserDB;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


/**
 *
 * @author yenni
 */
@WebServlet(name = "UpdateCliente", urlPatterns = {"/UpdateCliente"})
@jakarta.servlet.annotation.MultipartConfig 
public class UpdateCliente extends HttpServlet {
    private ServletUtil util;
    public UpdateCliente(){
        util = new ServletUtil();
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("ID")) ;
            System.out.println(id + "id from updateCliente");
            UserDB userDB = new UserDB(request.getSession());
            //User currentUser = userDB.obtener(id).get();
            ClienteDB clienteDB = new ClienteDB(request.getSession());
            String error = clienteDB.updateIntoDB(request, id);
            if (error != null) {
                request.setAttribute("updateError", error);
            } else {
                request.setAttribute("currentUser",userDB.obtener(id).get() );
                request.setAttribute("exito", "perfil actualizado");
            }
        } catch (NoConnectionFoundException | NumberFormatException ex) {
            request.setAttribute("error", "conexion no encontrada");
        }
        request.setAttribute("cliente", true);
        util.redirect(this, request, response, "/gerenteModule/updateInfo.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
