package ipc2.bank.util;

import ipc2.bank.database.UserDB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author yenni
 */
public class utilForServlet {

    public void goToValidateSchedule(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response, String urlValid) 
            throws ServletException, IOException {
        try {
            UserDB userDB = new UserDB(request.getSession());
            if (userDB.validateScedule(request.getSession())) {
                redirect(servlet, request, response, urlValid);
            } else {
                request.setAttribute("error",
                        "El usuario no se encuentra de turno");
                redirect(servlet, request, response, "/dashboard.jsp");
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            redirect(servlet, request, response, "/dashboard.jsp");
        }
    }
    public void redirect(HttpServlet servlet, HttpServletRequest request, 
            HttpServletResponse response, String url) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = servlet.getServletContext()
                .getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
