package model;

import java.sql.Date;

public class Medico {
    int codiceRegionale;
    String nome;
    String cognome;
    String cf;
    Date dataNascita;

    public int getCodiceRegionale() {
        return codiceRegionale;
    }

    public void setCodiceRegionale(int codiceRegionale) {
        this.codiceRegionale = codiceRegionale;
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
}
