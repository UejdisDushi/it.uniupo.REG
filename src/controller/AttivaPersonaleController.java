package controller;
import db.DBManager;
import model.Farmacia;
import model.Login;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AttivaPersonaleController extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        Farmacia farmacia = (Farmacia) form;

        Farmacia personaleDaInserire = new Farmacia();
        personaleDaInserire.setNomePersonale(farmacia.getNomePersonale());
        personaleDaInserire.setCognome(farmacia.getCognome());
        personaleDaInserire.setCf(farmacia.getCf());
        personaleDaInserire.setDataNascita((java.sql.Date) farmacia.getDataNascita());
        personaleDaInserire.setRuolo(farmacia.getRuolo());

        //login che recupero dal form
        Login login = new Login();
        login.setUser(farmacia.getUser());
        login.setPassword(farmacia.getPassword());

        DBManager dbManager = new DBManager();
        if(dbManager.attivaCollaboratore(personaleDaInserire, login,(int) request.getSession().getAttribute("id-farmacia"))) {
            return mapping.findForward("inserimento-farmacia-corretto");
        } else return mapping.findForward("inserimento-farmacia-errato");

    }
}

