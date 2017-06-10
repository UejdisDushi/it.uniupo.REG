package controller;

import db.DBManager;
import model.Login;
import model.Prodotti;
import model.Rimanenze;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;


public class VenditaController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");

        //ottengo le quantita dal form
        String[] quantita = request.getParameterValues("quantita");

        //è un test, lo metto in sessione, dopo vedo se toglierlo
        request.getSession().setAttribute("quantita", quantita);

        DBManager dbManager = new DBManager();
        ArrayList<Prodotti> prodotti = dbManager.getProdottiInMagazzino(idFarmacia);

        //rifare, devo fare in modo che basta che un elemento abbia la ricetta a true e deve rimandarmi su acquisto-con-ricetta

        for(int i = 0;i<prodotti.size();i++) {
            if(!quantita[i].equals(""))
                if(!prodotti.get(i).isRicetta())
                    if(dbManager.setVendita(idFarmacia,quantita,prodotti,((Login)request.getSession().getAttribute("login")).getUser(),false))
                        return mapping.findForward("acquisto-senza-ricetta");
                else return mapping.findForward("acquisto-con-ricetta");
        }

        return null;
    }
}
