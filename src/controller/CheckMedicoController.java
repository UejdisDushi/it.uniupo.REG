package controller;

import db.DBManager;
import model.Paziente;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;


public class CheckMedicoController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        //Paziente paziente = (Paziente) form;
        DBManager dbManager = new DBManager();
        ArrayList<Paziente> elencoPazienti = dbManager.getPazienti();

        String[] clienti = request.getParameterValues("cliente");

        for(int i = 0;i<clienti.length;i++)
            if(clienti[i].equals("Si"))
                request.getSession().setAttribute("cf-paziente",elencoPazienti.get(i).getCf());


        return mapping.findForward("elenco-medici");
    }
}
