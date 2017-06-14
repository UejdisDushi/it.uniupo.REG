package model;

import org.apache.struts.action.ActionForm;



public class Messaggio extends ActionForm {

    String mittente;
    String destinatario;
    String corpo;
    boolean visualizzato;
    java.sql.Date data;

    public java.sql.Date getData() {
        return data;
    }

    public void setData(java.sql.Date data) {
        this.data = data;
    }

    public boolean isVisualizzato() {
        return visualizzato;
    }

    public void setVisualizzato(boolean visualizzato) {
        this.visualizzato = visualizzato;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }
}
