package model;

import org.apache.struts.action.ActionForm;

import java.util.Date;


public class Farmacia extends ActionForm{

    private long idFarmacia;
    private String nomeFarmacia;
    private String cap;
    private String citta;
    private String numeroTelefono;
    private String provincia;
    private String via;
    private String nomePersonale;
    private String cognome;
    private String cf;          //controllare con js
    private java.sql.Date dataNascita;
    private String ruolo;       //da mettere in javascript il controllo, potendo scegliere tra le 4 opzioni

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;




    public long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public void setNomeFarmacia(String nome) {
        this.nomeFarmacia = nome;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNomePersonale() {
        return nomePersonale;
    }

    public void setNomePersonale(String nome) {
        this.nomePersonale = nome;
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
