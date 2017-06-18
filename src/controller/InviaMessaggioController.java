package controller;

import db.DBManager;
import model.Login;
import model.Messaggio;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class InviaMessaggioController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        Messaggio messaggio = (Messaggio) form;
        DBManager dbManager = new DBManager();
        String mittente = ((Login)request.getSession().getAttribute("login")).getUser();
        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        if(dbManager.nuovoMessaggio(mittente, messaggio.getDestinatario(), messaggio.getCorpo(), idFarmacia)){
            request.setAttribute("redirect", "inserimento-corretto");
            return mapping.findForward("redirect");
        }
        else {
            request.setAttribute("redirect", "inserimento-errato");
            return mapping.findForward("redirect");
        }
    }
}
