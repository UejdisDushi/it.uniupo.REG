package controller;

import db.DBManager;
import model.Login;
import model.Prodotti;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;


public class VenditaController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");

        //lo uso per capire se l'ordine contiene ALMENO un prodotto con obbligo di ricetta
        boolean rimandaARicetta = false;

        String cf = "";
        String ruolo = "";

        //ottengo le quantita dal form
        String[] quantita = request.getParameterValues("quantita");

        DBManager dbManager = new DBManager();
        ArrayList<Prodotti> prodotti = dbManager.getProdottiInMagazzino(idFarmacia);

        //controllo se i campi compilati, quindi i prodotti da vendere, siano diversi da "" e a quel punto se uno di questi prodotti necessità di ricetta imposto true
        for(int i = 0;i<prodotti.size();i++)
            if (!quantita[i].equals(""))
                if (prodotti.get(i).isRicetta())
                    rimandaARicetta = true;

        //check per verificare se è stata selezionato almeno una quantità
        boolean nessunaQta = true;
        for(int i =0; i<quantita.length;i++) {
            if (quantita[i].equals("")) {}
            else nessunaQta = false;
        }
        if(nessunaQta) {
            request.setAttribute("redirect", "nessuna-quantità");
            return mapping.findForward("redirect");
        }

        //serve per controllare se una quantità non è ammessa, perchè maggiore al magazzino
        for(int i = 0;i<quantita.length;i++)
            if(!quantita[i].equals(""))
                if(Integer.parseInt(quantita[i]) > dbManager.getQTAInMagazzino(idFarmacia, prodotti.get(i).getId())) {
                    request.setAttribute("redirect", "valore-non-consentito");
                    return mapping.findForward("redirect");
                }

        //recupero tramite l'ordine appena piazzato il suo id, in modo da creare la ricetta con le informazioni necessarie
        int idOrdine = dbManager.setVendita(idFarmacia,quantita,prodotti,((Login)request.getSession().getAttribute("login")).getUser(),false);
        request.getSession().setAttribute("id-ordine",idOrdine);

        //se l'ordine è con ricetta allora passo al controllo dei pazienti, altrimenti ritorno nella home
        if(rimandaARicetta) {
            return mapping.findForward("acquisto-con-ricetta");
        }

        else {
            cf = dbManager.getCFByUsername(((Login) request.getSession().getAttribute("login")).getUser());
            ruolo = dbManager.getRuoloByCF(cf);
            if(ruolo.equals("tf"))
                return mapping.findForward("acquisto-senza-ricetta-tf");
            else return mapping.findForward("acquisto-senza-ricetta-df");
        }
    }
}
