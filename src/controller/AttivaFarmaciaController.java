package controller;
import db.DBManager;
import model.Farmacia;
import model.Login;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AttivaFarmaciaController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        Login loggato = (Login) request.getSession().getAttribute("login");
        //if(loggato.getRule == REG) puoi inserire
        Farmacia farmacia = (Farmacia) form;

        Farmacia farmaciaDaInserire = new Farmacia();
        farmaciaDaInserire.setNome(farmacia.getNome());
        farmaciaDaInserire.setCap(farmacia.getCap());
        farmaciaDaInserire.setCitta(farmacia.getCitta());
        farmaciaDaInserire.setNumeroTelefono(farmacia.getNumeroTelefono());
        farmaciaDaInserire.setProvincia(farmacia.getProvincia());
        farmaciaDaInserire.setVia(farmacia.getVia());

        DBManager dbManager = new DBManager();
        if(dbManager.attivaFarmaciaAndTF(farmaciaDaInserire)) {
            return mapping.findForward("inserimento-farmacia-corretto");
        } else return mapping.findForward("inserimento-farmacia-errato");

    }
}
