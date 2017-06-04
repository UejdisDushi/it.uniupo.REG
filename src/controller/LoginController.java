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

        String ruolo = dbManager.validate(utente,password);

        //recupero cf per repcuperare personale e poi id farmacia, per permettere inserimento collaboratore
        String cf = dbManager.getCF(utente);

        //recupero id farmacia tramite cf
        int idFarmacia = dbManager.getIdFarmacia(cf);

        //Rimanenze rimanenze = new Rimanenze();
        //rimanenze.setIdFarmacia(idFarmacia);

        //request.getSession().setAttribute("utenteLoggato", login);

        request.getSession().setAttribute("id-farmacia",idFarmacia);
        switch (ruolo) {
            case "adm":
                return mapping.findForward("home-adm");
            case "tf":
                //request.getSession().setAttribute("rimanenze",rimanenze);
                ArrayList<Prodotti> prodotti=  dbManager.getTuttiProdotti();
                request.getSession().setAttribute("elenco-prodotti",prodotti);
                return mapping.findForward("home-tf");
            case "ob":
                ArrayList<Prodotti> venditaOB = dbManager.getOBMagazzino(idFarmacia);
                ArrayList<Rimanenze> rimanenze = dbManager.getRimanenzeByIdFarmacia(idFarmacia);
                request.getSession().setAttribute("vendita-prodotti-per-ob-qta", rimanenze);
                request.getSession().setAttribute("vendita-prodotti-per-ob", venditaOB);
                return mapping.findForward("home-ob");
            case "df":
                return mapping.findForward("home-df");
            default:
                return mapping.findForward("dati-errati");
        }

    }
}
