package model;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;

import java.util.Date;

public class Personale extends ActionForm{
    public String getNomePersonale() {
        return nomePersonale;
    }

    public void setNomePersonale(String nomePersonale) {
        this.nomePersonale = nomePersonale;
    }

    String nomePersonale;
    String cognome;
    String cf;          //controllare con js
    java.sql.Date dataNascita;
    String ruolo;       //da mettere in javascript il controllo, potendo scegliere tra le 4 opzioni


    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public java.sql.Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(java.sql.Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
