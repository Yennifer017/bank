
package ipc2.bank.servlets.gerente;

import ipc2.bank.database.LimiteDB;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import ipc2.bank.models.Limite;
import java.util.List;

/**
 *
 * @author yenni
 */
@WebServlet(name = "SystemParam", urlPatterns = {"/SystemParam"})
public class SystemParam extends HttpServlet {
    private ServletUtil util;
    public SystemParam() {
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
        setLimits(request);
        util.goToValidateSchedule(this, request, response, "/gerenteModule/gestionParameters.jsp");
    }
    private void setLimits(HttpServletRequest request){
        LimiteDB limDB = new LimiteDB((Connection) request.getSession().getAttribute("conexion"));
        List<Limite> limites = limDB.getAll();
        request.setAttribute("limites", limites);
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
        LimiteDB limDB = new LimiteDB((Connection)request.getSession().getAttribute("conexion"));
        String error = limDB.updateIntoDB(request);
        if(error != null){
            request.setAttribute("updateError", error);
        }else{
            request.setAttribute("exito", "Actualizacion exitosa");
        }
        request.setAttribute("limites", limDB.getAll());
        util.goToValidateSchedule(this, request, response, "/gerenteModule/gestionParameters.jsp");
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
