package controller;

import db.DBManager;
import model.Prodotti;
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

        boolean nessunaQta = true;
        for(int i =0; i<quantita.length;i++) {
            if (quantita[i].equals("")) {}
            else nessunaQta = false;
        }

        if(nessunaQta) {
            request.setAttribute("redirect", "reintegra-no");
            return mapping.findForward("redirect");
        }

        if(dbManager.reintegra(quantita, idFarmacia, prodotti)) {
            request.setAttribute("redirect", "reintegra-ok");
            return mapping.findForward("redirect");
        }
        return null;
    }
}
