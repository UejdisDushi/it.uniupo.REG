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
            String cf = dbManager.getCF(mittente);
            String ruolo = dbManager.getRuoloByCF(cf);
            switch (ruolo) {
                case "tf":
                    return mapping.findForward("messaggio-inviato-tf");
                case "ob":
                    return mapping.findForward("messaggio-inviato-ob");
                case "df":
                    return mapping.findForward("messaggio-inviato-df");
                default:
                    return mapping.findForward("messaggio-inviato-REG");
            }
        }
        return null;
    }
}
