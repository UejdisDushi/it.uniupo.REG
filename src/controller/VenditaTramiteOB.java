package controller;

import db.DBManager;
import model.Login;
import model.Prodotti;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;

public class VenditaTramiteOB extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        //ottengo le quantita dal form
        String[] quantita = request.getParameterValues("quantita");

        DBManager dbManager = new DBManager();
        if(dbManager.setVenditaPerOB((int) request.getSession().getAttribute("id-farmacia"), quantita, (ArrayList< Prodotti>) request.getSession().getAttribute("prodotti-dentro-magazzino-farmacia"),((Login)request.getSession().getAttribute("login")).getUser()))
            return mapping.findForward("vendita-tramite-ob-ok");
        return null;
    }
}
