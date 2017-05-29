package model;

import org.apache.struts.action.ActionForm;


public class Farmacia extends ActionForm{

    private long idFarmacia;
    private String nome;
    private String cap;
    private String citta;
    private String numeroTelefono;
    private String provincia;
    private String via;

    public long getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(long idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
