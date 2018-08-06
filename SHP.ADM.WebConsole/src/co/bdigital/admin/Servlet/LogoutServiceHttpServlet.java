package co.bdigital.admin.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.bdigital.admin.util.ConstantADM;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Servlet implementation class logoutServiceHttpServlet
 */

@WebServlet(name = "logoutServiceHttpServlet", urlPatterns = { "/logoutService" })
public class LogoutServiceHttpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static CustomLogger logger;

    /**
     * Default constructor.
     */
    public LogoutServiceHttpServlet() {
        logger = new CustomLogger(this.getClass());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            request.logout();
        } catch (Exception e) {

            logger.error(
                    ErrorEnum.LDAP_ERROR,
                    ConstantADM.LOGIN_SERVLET_ERROR_AUTHENTICATION
                            + e.getMessage(), e);

        }
    }

}