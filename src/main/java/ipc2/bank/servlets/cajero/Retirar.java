
package ipc2.bank.servlets.cajero;

import ipc2.bank.models.Transaccion;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author yenni
 */
@WebServlet(name = "Retirar", urlPatterns = {"/Retirar"})
public class Retirar extends HttpServlet {
    private ServletUtil util;
    public Retirar() {
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
        setAtributes(request);
        util.goToValidateSchedule(this, request, response, "/cajeroModule/transacciones.jsp");
    }

    private void setAtributes(HttpServletRequest request){
        request.setAttribute("title", "Retiro");
        request.setAttribute("linkToShowDPI", "ShowDPI");
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
        setAtributes(request);
        util.processTransaction(request, Transaccion.DEBITO);
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
