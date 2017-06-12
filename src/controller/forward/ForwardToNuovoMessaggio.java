package controller.forward;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Created by Edi on 12/06/2017.
 */
public class ForwardToNuovoMessaggio extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        return mapping.findForward("nuovo-messaggio");
    }
}
