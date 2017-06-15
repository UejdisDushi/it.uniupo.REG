package controller.forward;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Created by Edi on 15/06/2017.
 */
public class ForwardToVisualizzaMessaggi extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        return mapping.findForward("visualizza-messaggi");
    }
}
