package ipc2.bank.servlets.gerente;

import ipc2.bank.database.UserDB;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.User;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yenni
 */
@WebServlet(name = "DisplayCajeros", urlPatterns = {"/DisplayCajeros"})
public class DisplayCajeros extends HttpServlet {

    private ServletUtil util;

    public DisplayCajeros() {
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
        util.displayUsers(this, request, response, "GESTION DE CAJEROS", UserDB.CAJERO);
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
            request.setAttribute("url", "UpdateCajero"); //el servlet que procesara el update
            request.setAttribute("title", "ACTUALIZACION DE UN CAJERO");
            UserDB userDB = new UserDB(request.getSession());
            User currentUser = userDB.obtener(Integer.parseInt(request.getParameter("idCurrentUser"))).get();
            request.setAttribute("currentUser", currentUser);
            util.loadFilterTurns(request);
        } catch (NoConnectionFoundException ex) {
            Logger.getLogger(DisplayCajeros.class.getName()).log(Level.SEVERE, null, ex);
        }
        util.goToValidateSchedule(this, request, response, "/gerenteModule/updateInfo.jsp");
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
