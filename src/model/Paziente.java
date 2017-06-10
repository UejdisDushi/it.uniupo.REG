package model;


import java.sql.Date;

public class Paziente {

    String cf;
    String nome;
    String cognome;
    Date dataDiNAscita;
    String personale;


    public String getPersonale() {
        return personale;
    }

    public void setPersonale(String personale) {
        this.personale = personale;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

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

    public Date getDataDiNAscita() {
        return dataDiNAscita;
    }

    public void setDataDiNAscita(Date dataDiNAscita) {
        this.dataDiNAscita = dataDiNAscita;
    }
}
