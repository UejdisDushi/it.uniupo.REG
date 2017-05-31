package model;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;

import java.util.Date;

public class Personale extends ActionForm{
    String nome;
    String cognome;
    String cf;          //controllare con js
    Date dataNascita;
    String ruolo;       //da mettere in javascript il controllo, potendo scegliere tra le 4 opzioni


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
