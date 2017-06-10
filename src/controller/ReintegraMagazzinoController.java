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

public class ReintegraMagazzinoController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        String[] quantita = request.getParameterValues("quantita");
        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        DBManager dbManager = new DBManager();
        ArrayList<Prodotti> prodotti = dbManager.getTuttiProdotti();

        if(dbManager.reintegra(quantita, idFarmacia, prodotti))
            return mapping.findForward("reintegra-ok");
        return null;
    }
}
