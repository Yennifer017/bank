
package ipc2.bank.servlets.usuarios;

import ipc2.bank.database.CuentaDB;
import ipc2.bank.models.Cuenta;
import ipc2.bank.models.User;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author yenni
 */
@WebServlet(name = "AcountStatus", urlPatterns = {"/AcountStatus"})
public class AcountStatus extends HttpServlet {
    private ServletUtil util;
    public AcountStatus(){
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
        User currentUser = util.getCurrentUser(request);
        CuentaDB cuentaDB = new CuentaDB(util.getConnection(request));
        List<Cuenta> cuentas = cuentaDB.getCuentas(currentUser.getId());
        request.setAttribute("cuentas", cuentas);
        util.redirect(this, request, response, "/userModule/detailsAcounts.jsp");
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
