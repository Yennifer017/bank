
package ipc2.bank.servlets.cajero;

import ipc2.bank.database.TransaccionDB;
import ipc2.bank.models.User;
import ipc2.bank.models.Transaccion;
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
@WebServlet(name = "Depositar", urlPatterns = {"/Depositar"})
public class Depositar extends HttpServlet {
    private ServletUtil util;
    
    public Depositar(){
        this.util = new ServletUtil();
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
        request.setAttribute("title", "Deposito");
        util.goToValidateSchedule(this, request, response, "/cajeroModule/transacciones.jsp");
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
        request.setAttribute("title", "Deposito");
        util.processTransaction(request, Transaccion.CREDITO);
        util.redirect(this, request, response, "/cajeroModule/transacciones.jsp");
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
