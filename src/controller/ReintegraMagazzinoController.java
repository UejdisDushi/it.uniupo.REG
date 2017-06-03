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

        Rimanenze rimanenze = (Rimanenze) form;

        DBManager dbManager = new DBManager();
        if(dbManager.reintegra(rimanenze.getQuantita(), (int)request.getSession().getAttribute("id-farmacia"), (ArrayList<Prodotti>)request.getSession().getAttribute("elenco-prodotti")))
            return mapping.findForward("reintegra-ok");
        else
            return mapping.findForward("reintegra-no");
    }
}
