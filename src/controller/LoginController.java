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


public class LoginController extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        //if(request.getSession().getAttribute("login") != null)
          //  response.sendRedirect("WEB-INF/attiva-farmacia.jsp");

        Login login = (Login) form;
        String utente = login.getUser();
        String password = login.getPassword();
        DBManager dbManager = new DBManager();

        //recupero ruolo tramite credenziali
        String ruolo = dbManager.validate(utente,password);

        //recupero cf per repcuperare personale e poi id farmacia, per permettere inserimento collaboratore
        String cf = dbManager.getCF(utente);

        //recupero id farmacia dove lavora il personale tramite cf e lo metto in sessione, in quanto viene usato quasi ovunque
        int idFarmacia = dbManager.getIdFarmacia(cf);
        request.getSession().setAttribute("id-farmacia",idFarmacia);

        switch (ruolo) {
            case "adm":
                return mapping.findForward("home-adm");
            case "tf":

                ArrayList<Prodotti> tuttiIProdotti=  dbManager.getTuttiProdotti();
                request.getSession().setAttribute("tutti-i-prodotti",tuttiIProdotti);
                return mapping.findForward("home-tf");
            case "ob":
                return mapping.findForward("home-ob");
            case "df":
                return mapping.findForward("home-df");
            default:
                return mapping.findForward("dati-errati");
        }

    }
}
