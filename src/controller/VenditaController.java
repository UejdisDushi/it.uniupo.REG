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


public class VenditaController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        boolean rimandaARicetta = false;

        //ottengo le quantita dal form
        String[] quantita = request.getParameterValues("quantita");

        DBManager dbManager = new DBManager();
        ArrayList<Prodotti> prodotti = dbManager.getProdottiInMagazzino(idFarmacia);
        
        for(int i = 0;i<prodotti.size();i++)
            if (!quantita[i].equals(""))
                if (prodotti.get(i).isRicetta())
                    rimandaARicetta = true;

        if(dbManager.setVendita(idFarmacia,quantita,prodotti,((Login)request.getSession().getAttribute("login")).getUser(),false))
            if(rimandaARicetta)
                return mapping.findForward("acquisto-con-ricetta");
            else
                return mapping.findForward("acquisto-senza-ricetta");

        return null;
    }
}
