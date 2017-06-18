package controller.forward;

import db.DBManager;
import model.Login;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ForwardToHome extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        DBManager dbManager = new DBManager();
        String cf = dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser());
        String ruolo = dbManager.getRuoloByCF(cf);
        switch (ruolo){
            case "tf":
                return mapping.findForward("home-tf");
            case "df":
                return mapping.findForward("home-df");
            case "ob":
                return mapping.findForward("home-ob");
            default:
                return mapping.findForward("home-REG");
        }
    }
}
