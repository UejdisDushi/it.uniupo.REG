package controller;

import db.DBManager;
import model.Login;
import model.Medico;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;


public class TerminaOperazioneVenditaController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        DBManager dbManager = new DBManager();
        ArrayList<Medico> elencoMedici = dbManager.getMedici();
        String cfPaziente = (String)request.getSession().getAttribute("cf-paziente");
        int idOrdine = (int)request.getSession().getAttribute("id-ordine");

        boolean nonSelezionato = true;
        String[] medici = request.getParameterValues("medico");
        for(int i = 0;i<medici.length;i++)
            if(medici[i].equals("Si")) {
                nonSelezionato = false;
                if(dbManager.setRicetta(elencoMedici.get(i).getCodiceRegionale(),cfPaziente,idOrdine)){}
            }

        if(nonSelezionato) {
            request.setAttribute("redirect", "medico-non-selezionato");
            return mapping.findForward("redirect");
        }

        Double totale = dbManager.getTotaleByIdOrdine(idOrdine);
        request.setAttribute("totale", totale.toString());
        return mapping.findForward("termina-vendita");
    }
}
