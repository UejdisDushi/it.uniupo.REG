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

        //ottengo il cf del personale che registra il nuovo paziente
        String userCheRegistraPaziente = dbManager.getCFByUsername((String)((Login)request.getSession().getAttribute("login")).getUser());
        ArrayList<Paziente> elencoPazienti = dbManager.getPazienti();

        String[] clienti = request.getParameterValues("cliente");

        //check per verificare se è stato selezionato almeno un paziente
        boolean nessunPaziente = true;
        for(int i = 0;i<clienti.length;i++)
            if(clienti[i].equals("")) {}
            else nessunPaziente = false;

        if(nessunPaziente) {
            request.setAttribute("redirect", "nessun-paziente");
            return mapping.findForward("redirect");
        }

        //check per verificare che non venga selezionato più di un paziente
        int piuPazienti = 0;
        for(int i = 0;i<clienti.length;i++)
            if(clienti[i].equals("Si")) piuPazienti++;

        if(piuPazienti > 1) {
            request.setAttribute("redirect", "piu-pazienti");
            return mapping.findForward("redirect");
        }


        //check per vedere se paziente esiste già
        if(paziente.getCf()!=null) {
            for (int i = 0; i < elencoPazienti.size(); i++)
                if (elencoPazienti.get(i).getCf().equals(paziente.getCf().trim())) {
                    request.setAttribute("redirect", "paziente-già-registrato");
                    return mapping.findForward("redirect");
                }
        }

        //la varibile clienti, sarà pari al numero di clienti nella PAGINA jsp, quindi se nel db ho 3 clienti, ma nella pagina genero un nuovo cliente allora la variabile clienti sarà maggiore di 1 rispetto
        //al numero di clienti nel db, per risolvere il problema inserisco subito il paziente al db e riottengo il numero dei pazienti
        //che ora sarà uguale in size alla variabile clienti
        if(clienti.length > elencoPazienti.size())
            if(dbManager.inserisciNuovoPaziente(paziente.getCf(),paziente.getNome(),paziente.getCognome(),paziente.getDataDiNAscita(),userCheRegistraPaziente)){}

        elencoPazienti = dbManager.getPazienti();

        //tengo traccia del cliente che ha effettuato l'ordine e metto in sessione il suo cf
        for(int i = 0;i<clienti.length;i++)
            if(clienti[i].equals("Si")) {
                request.getSession().setAttribute("cf-paziente", elencoPazienti.get(i).getCf());
            }

        return mapping.findForward("elenco-medici");
    }
}
