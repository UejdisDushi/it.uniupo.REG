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
        ArrayList<Prodotti> prodotti = new ArrayList<Prodotti>();
        prodotti = dbManager.getProdotti();
        request.getSession().setAttribute("elenco-prodotti",prodotti);
        request.getSession().setAttribute("id-farmacia",idFarmacia);
        switch (ruolo) {
            case "adm":
                return mapping.findForward("home-adm");
            case "tf":
                //request.getSession().setAttribute("rimanenze",rimanenze);

                return mapping.findForward("home-tf");
            case "op":
                return mapping.findForward("home-op");
            case "df":
                return mapping.findForward("home-df");
            default:
                return mapping.findForward("dati-errati");
        }

    }
}
