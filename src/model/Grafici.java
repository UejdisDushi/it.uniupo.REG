package model;

import org.apache.struts.action.ActionForm;

import java.sql.Date;


public class Grafici extends ActionForm {

    private Date da;
    private Date a;

    public Date getDa() {
        return da;
    }

    public void setDa(Date da) {
        this.da = da;
    }

    public Date getA() {
        return a;
    }

    public void setA(Date a) {
        this.a = a;
    }
}
