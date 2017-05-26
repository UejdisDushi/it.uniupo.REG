package controller;

import db.DBManager;
import model.LoginForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        LoginForm login = (LoginForm) form;
        String utente = login.getUser();
        String password = login.getPassword();
        DBManager dbManager = new DBManager();
        if(dbManager.validate(utente,password))
            return mapping.findForward("successLogin");
        else
            return mapping.findForward("badLogin");
    }
}
