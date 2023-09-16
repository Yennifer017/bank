package ipc2.bank.servlets.gerente;

import ipc2.bank.database.ClienteDB;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 *
 * @author yenni
 */
@WebServlet(name = "CreateUser", urlPatterns = {"/CreateUser"})
@jakarta.servlet.annotation.MultipartConfig
public class CreateUser extends HttpServlet {

    private ServletUtil util;

    public CreateUser() {
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
        request.removeAttribute("error");
        util.goToValidateSchedule(this, request, response, "/gerenteModule/createProfile.jsp");
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
        request.removeAttribute("createError");
        request.removeAttribute("exito");
        ClienteDB clienteDB = new ClienteDB((Connection) request.getSession().getAttribute("conexion"));
        String error = clienteDB.InsertIntoDB(request);
        if(error != null){
            request.setAttribute("createError", error);
        }else{
            request.setAttribute("exito", "Se ha creado el usuario");
        }
        util.redirect(this, request, response, "/gerenteModule/createProfile.jsp");
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
