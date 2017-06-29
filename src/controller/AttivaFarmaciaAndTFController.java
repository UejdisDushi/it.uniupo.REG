package controller;
import com.sun.deploy.perf.PerfRollup;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import db.DBManager;
import model.Farmacia;
import model.Login;
import model.Personale;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Date;


public class AttivaFarmaciaAndTFController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        Farmacia farmacia = (Farmacia) form;

        Farmacia farmaciaDaInserire = new Farmacia();
        farmaciaDaInserire.setNomeFarmacia(farmacia.getNomeFarmacia());
        farmaciaDaInserire.setCap(farmacia.getCap());
        farmaciaDaInserire.setCitta(farmacia.getCitta());
        farmaciaDaInserire.setNumeroTelefono(farmacia.getNumeroTelefono());
        farmaciaDaInserire.setProvincia(farmacia.getProvincia());
        farmaciaDaInserire.setVia(farmacia.getVia());

        //recupero l'anagrafica del titolare creato
        Personale personaleDaInserire = new Personale();
        personaleDaInserire.setNomePersonale(farmacia.getNomePersonale());
        personaleDaInserire.setCognome(farmacia.getCognome());
        personaleDaInserire.setCf(farmacia.getCf());
        personaleDaInserire.setDataNascita(farmacia.getDataNascita());
        personaleDaInserire.setRuolo(farmacia.getRuolo());

        //recupero le credenziali assegnate al tf creato
        Login credenzialiDiLogin = new Login();
        credenzialiDiLogin.setUser(farmacia.getUser());
        credenzialiDiLogin.setPassword(farmacia.getPassword());

        DBManager dbManager = new DBManager();
        if(dbManager.attivaFarmaciaAndTF(farmaciaDaInserire, personaleDaInserire, credenzialiDiLogin)) {
            request.setAttribute("redirect", "inserimento-corretto");
            return mapping.findForward("redirect");
        } else {
            request.setAttribute("redirect", "inserimento-errato");
            return mapping.findForward("redirect");
        }

    }
}
