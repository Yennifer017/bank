
package ipc2.bank.servlets.gerente;

import ipc2.bank.database.EmpleadoDB;
import ipc2.bank.database.TurnoDB;
import ipc2.bank.database.UserDB;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.User;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author yenni
 */
@WebServlet(name = "UpdateInfo", urlPatterns = {"/UpdateInfo"})
@jakarta.servlet.annotation.MultipartConfig
public class UpdateInfo extends HttpServlet {
    private ServletUtil util;
    public UpdateInfo() {
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
        request.setAttribute("title", "Edicion de perfil personal");
        request.setAttribute("currentUser", request.getSession().getAttribute("user"));
        util.loadFilterTurns(request);
        util.goToValidateSchedule(this, request, response, "/gerenteModule/updateInfo.jsp");
        
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
        request.setAttribute("title", "Edicion de perfil personal");
        User currentUser = (User) request.getSession().getAttribute("user");
        util.loadFilterTurns(request);
        try {
            EmpleadoDB empleadoDB = new EmpleadoDB(request.getSession());
            String error = empleadoDB.updateIntoDB(request, UserDB.GERENTE, currentUser.getId());
            if(error != null){
                request.setAttribute("updateError", error);
            }else{
                currentUser = new UserDB(request.getSession()).obtener(currentUser.getId()).get();
                request.getSession().setAttribute("user", currentUser);
                request.setAttribute("currentUser", request.getSession().getAttribute("user"));
                request.setAttribute("exito", "perfil actualizado");
            }
        } catch (NoConnectionFoundException ex) {
            request.setAttribute("error", "conexion no encontrada");
        }
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
