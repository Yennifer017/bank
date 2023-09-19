
package ipc2.bank.servlets.cajero;

import ipc2.bank.util.PdfUtil;
import ipc2.bank.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author yenni
 */
@WebServlet(name = "ShowDPI", urlPatterns = {"/ShowDPI"})
public class ShowDPI extends HttpServlet {

    private ServletUtil util;
    public ShowDPI(){
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
        util.redirect(this, request, response, "/cajeroModule/showDPI.jsp");
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
        util.redirect(this, request, response, "/cajeroModule/showDPI.jsp");
        PdfUtil pdfU = new PdfUtil(util.getConnection(request));
        try {
            pdfU.showDPI(request, response);
        } catch (NumberFormatException | SQLException ex) {
            Logger.getLogger(ShowDPI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
