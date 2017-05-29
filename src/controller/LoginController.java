package controller;

import db.DBManager;
import model.Login;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginController extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        //if(request.getSession().getAttribute("login") != null)
          //  response.sendRedirect("WEB-INF/attiva-farmacia.jsp");

        Login login = (Login) form;
        String utente = login.getUser();
        String password = login.getPassword();
        DBManager dbManager = new DBManager();
        if(dbManager.validate(utente,password)) {

            return mapping.findForward("successLogin");
        }
        else
            return mapping.findForward("badLogin");
    }
}
