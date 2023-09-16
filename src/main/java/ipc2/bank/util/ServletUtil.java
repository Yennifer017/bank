package ipc2.bank.util;

import ipc2.bank.database.TurnoDB;
import ipc2.bank.database.UserDB;
import ipc2.bank.exceptions.NoConnectionFoundException;
import ipc2.bank.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author yenni
 */
public class ServletUtil {

    public void goToValidateSchedule(HttpServlet servlet, HttpServletRequest request, 
            HttpServletResponse response, String urlValid) 
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
    
    public void displayUsers(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response,
            String title, int typeUser) 
            throws ServletException, IOException{
        UserDB userDB = new UserDB((Connection)request.getSession().getAttribute("conexion"));
        List<User> consulta = userDB.getAll(typeUser);
        request.setAttribute("consultas", consulta);
        request.setAttribute("titulo", title);
        this.goToValidateSchedule(servlet, request, response, "/gerenteModule/gestionUsers.jsp");
    }
    public void loadFilterTurns(HttpServletRequest request){
        try {
            request.setAttribute("turns", new TurnoDB(request.getSession()).getAll());
        } catch (NoConnectionFoundException ex) {
            System.out.println(ex);
        }
    }
}
