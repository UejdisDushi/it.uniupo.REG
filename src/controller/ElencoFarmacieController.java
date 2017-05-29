package controller;

import db.DBManager;
import model.Farmacia;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;

public class ElencoFarmacieController extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        DBManager dbManager = new DBManager();
        List<Farmacia> farmacie = dbManager.elencoFarmacie();
        request.setAttribute("farmacie", farmacie);
        return mapping.findForward("farmacie");
    }
}
