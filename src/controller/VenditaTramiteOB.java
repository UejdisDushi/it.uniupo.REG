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

        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");

        DBManager dbManager = new DBManager();
        ArrayList<Prodotti> prodotti = dbManager.getProdottiInMagazzino(idFarmacia);

        //rimuovo dalla lista i prodotti che necessitano della ricetta
        for (int j = 0; j < prodotti.size(); j++) {
            if (prodotti.get(j).isRicetta() == true) {
                prodotti.remove(j);
            }
        }

        //serve per controllare se una quantità non è ammessa, perchè maggiore al magazzino
        for(int i = 0;i<quantita.length;i++)
            if(!quantita[i].equals(""))
                if(Integer.parseInt(quantita[i]) > dbManager.getQTAInMagazzino(idFarmacia, prodotti.get(i).getId())) {
                    request.setAttribute("redirect", "valore-non-consentito");
                    return mapping.findForward("valore-non-consentito");
            }

        int idOrdine = dbManager.setVendita(idFarmacia, quantita, prodotti,((Login)request.getSession().getAttribute("login")).getUser(),true);

        return mapping.findForward("vendita-tramite-ob-ok");
    }
}
