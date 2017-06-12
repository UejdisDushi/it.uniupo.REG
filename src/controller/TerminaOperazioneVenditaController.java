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

        String[] medici = request.getParameterValues("medico");
        for(int i = 0;i<medici.length;i++)
            if(medici[i].equals("Si"))
                if(dbManager.setRicetta(elencoMedici.get(i).getCodiceRegionale(),cfPaziente,idOrdine)){}


        String cf = dbManager.getCFByUser(((Login) request.getSession().getAttribute("login")).getUser());
        String ruolo = dbManager.getRuoloByCF(cf);
        if(ruolo.equals("tf"))
            return mapping.findForward("termina-operazione-tf");
        else return mapping.findForward("termina-operazione-df");
    }
}
