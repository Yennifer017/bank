package ipc2.bank.servlets.usuarios;

import ipc2.bank.database.CuentaDB;
import ipc2.bank.models.Cuenta;
import ipc2.bank.models.User;
import ipc2.bank.util.ServletUtil;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author yenni
 */
@WebServlet(name = "Transferir", urlPatterns = {"/Transferir"})
public class Tranferir extends HttpServlet {

    private ServletUtil util;

    public Tranferir() {
        this.util = new ServletUtil();
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
        List<Cuenta> cuentas = new CuentaDB(util.getConnection(request))
                .getCuentas(currentUser.getId());
        request.setAttribute("acountsUser", cuentas);
        request.setAttribute("acountsTo", cuentas);
        request.setAttribute("title", "entre cuentas asociadas");
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
