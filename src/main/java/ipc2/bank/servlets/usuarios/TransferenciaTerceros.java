
package ipc2.bank.servlets.usuarios;

import ipc2.bank.database.CuentaDB;
import ipc2.bank.database.TransaccionDB;
import ipc2.bank.models.Cuenta;
import ipc2.bank.models.User;
import ipc2.bank.util.ServletUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.List;

/**
 *
 * @author yenni
 */
@WebServlet(name = "TransferenciaTerceros", urlPatterns = {"/TransferenciaTerceros"})
public class TransferenciaTerceros extends HttpServlet {
    private ServletUtil util;
    public TransferenciaTerceros(){
        util = new ServletUtil();
        
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = util.getCurrentUser(request);
        CuentaDB cuentaDB =  new CuentaDB(util.getConnection(request));
        request.setAttribute("acountsUser", cuentaDB.getCuentas(currentUser.getId()));
        request.setAttribute("acountsTo", cuentaDB.getCuentasAsociadas(currentUser.getId()));
        request.setAttribute("title", "entre cuentas de terceros");
        util.redirect(this, request, response, "/userModule/Transferencias.jsp");
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
        processRequest(request, response);
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
        util.loadTransaction(request);
        processRequest(request, response);
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
