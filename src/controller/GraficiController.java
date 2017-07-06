package controller;

import db.DBManager;
import model.Grafici;
import model.Login;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class GraficiController extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        Grafici grafici = (Grafici) form;
        DBManager db = new DBManager();
        request.setAttribute("grafici", grafici);
        String cf = db.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser());
        String ruolo = db.getRuoloByCF(cf);
        if(ruolo.equals(""))
            return mapping.findForward("home-adm");
        else
            return mapping.findForward("home-tf");
    }
}
