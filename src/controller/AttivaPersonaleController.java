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
        personaleDaInserire.setDataNascita(farmacia.getDataNascita());
        personaleDaInserire.setRuolo(farmacia.getRuolo());

        //login che recupero dal form, quindi le credenziali assegnata al collaboratore
        Login login = new Login();
        login.setUser(farmacia.getUser());
        login.setPassword(farmacia.getPassword());

        DBManager dbManager = new DBManager();
        int idFarmacia = (int) request.getSession().getAttribute("id-farmacia");
        if(dbManager.attivaCollaboratore(personaleDaInserire, login,idFarmacia)) {
            request.setAttribute("redirect", "inserimento-corretto");
            return mapping.findForward("redirect");
        } else {
            request.setAttribute("redirect", "inserimento-errato");
            return mapping.findForward("redirect");
        }
    }
}

