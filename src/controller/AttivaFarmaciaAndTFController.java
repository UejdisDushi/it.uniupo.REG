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
        Login loggato = (Login) request.getSession().getAttribute("login");
        //if(loggato.getRule == REG) puoi inserire
        Farmacia farmacia = (Farmacia) form;

        Farmacia farmaciaDaInserire = new Farmacia();
        farmaciaDaInserire.setNomeFarmacia(farmacia.getNomeFarmacia());
        farmaciaDaInserire.setCap(farmacia.getCap());
        farmaciaDaInserire.setCitta(farmacia.getCitta());
        farmaciaDaInserire.setNumeroTelefono(farmacia.getNumeroTelefono());
        farmaciaDaInserire.setProvincia(farmacia.getProvincia());
        farmaciaDaInserire.setVia(farmacia.getVia());

        Personale personaleDaInserire = new Personale();
        personaleDaInserire.setNomePersonale(farmacia.getNomePersonale());
        personaleDaInserire.setCognome(farmacia.getCognome());
        personaleDaInserire.setCf(farmacia.getCf());
        personaleDaInserire.setDataNascita((java.sql.Date) farmacia.getDataNascita());
        personaleDaInserire.setRuolo(farmacia.getRuolo());

        Login login = new Login();
        login.setUser(farmacia.getUser());
        login.setPassword(farmacia.getPassword());

        DBManager dbManager = new DBManager();
        if(dbManager.attivaFarmaciaAndTF(farmaciaDaInserire, personaleDaInserire, login)) {
            return mapping.findForward("inserimento-farmacia-corretto");
        } else return mapping.findForward("inserimento-farmacia-errato");

    }
}
