package co.bdigital.admin.Servlet;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.bdigital.admin.util.ConstantADM;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * Servlet implementation class LoginServiceHttpServlet
 */
@WebServlet(name = "loginServiceHttpServlet", urlPatterns = {
        "/loginServiceAuthentication" })
public class LoginServiceHttpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static CustomLogger logger;

    /**
     * Default constructor.
     */
    public LoginServiceHttpServlet() {
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

        String user;
        String pass;
        boolean isLogin;
        FacesContext context = FacesContext.getCurrentInstance();
        user = (String) context.getExternalContext().getRequestMap()
                .get(ConstantADM.J_USER_NAME).toString();
        pass = (String) context.getExternalContext().getRequestMap()
                .get(ConstantADM.J_PASS).toString();

        logger.info(ConstantADM.LOGIN_SERVLET_INICIO
                + ConstantADM.LOGIN_SERVLET_DO_POST);

        try {
            request.login(user, pass);
            isLogin = true;
        } catch (Exception e) {
            isLogin = false;
            logger.error(ConstantADM.LOGIN_SERVLET_ERROR_AUTHENTICATION, e);
        }

        context.getExternalContext().getRequestMap().put(ConstantADM.IS_LOGIN,
                isLogin);
    }

}
