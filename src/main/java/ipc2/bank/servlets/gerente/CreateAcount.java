
package ipc2.bank.servlets.gerente;

import ipc2.bank.database.CuentaDB;
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
@WebServlet(name = "CreateAcount", urlPatterns = {"/CreateAcount"})
public class CreateAcount extends HttpServlet {

    private ServletUtil util;
    public CreateAcount(){
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
        util.goToValidateSchedule(this, request, response, "/gerenteModule/createAcount.jsp");
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
        CuentaDB cuentaDb = new CuentaDB(util.getConnection(request));
        try {
            int idCuenta = cuentaDb.createAcount(request.getParameter("credentialsInput"), 
                    Float.parseFloat(request.getParameter("saldoInput")));
            request.setAttribute("exito", "Cuenta creada exitosamente, codigo: " + idCuenta);
        } catch (Exception e) {
            request.setAttribute("createAcountError", e.toString());
        }
        util.goToValidateSchedule(this, request, response, "/gerenteModule/createAcount.jsp");
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
