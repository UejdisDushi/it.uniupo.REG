package controller;

import db.DBManager;
import model.Login;
import model.Paziente;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;


public class CheckMedicoController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        Paziente paziente = (Paziente) form;
        DBManager dbManager = new DBManager();
        String userCheRegistraPaziente = dbManager.getCFByUser((String)((Login)request.getSession().getAttribute("login")).getUser());
        ArrayList<Paziente> elencoPazienti = dbManager.getPazienti();

        String[] clienti = request.getParameterValues("cliente");

        if(clienti.length > elencoPazienti.size())
            if(dbManager.inserisciNuovoPaziente(paziente.getCf(),paziente.getNome(),paziente.getCognome(),paziente.getDataDiNAscita(),userCheRegistraPaziente)){}

        elencoPazienti = dbManager.getPazienti();

        for(int i = 0;i<clienti.length;i++)
            if(clienti[i].equals("Si"))
                request.getSession().setAttribute("cf-paziente",elencoPazienti.get(i).getCf());


        return mapping.findForward("elenco-medici");
    }
}
