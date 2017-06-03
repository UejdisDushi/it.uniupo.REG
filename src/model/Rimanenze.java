package model;

import org.apache.struts.action.ActionForm;

import java.sql.Date;

public class Rimanenze extends ActionForm {

    private int quantita;
    private Date dataReintegro;
    private int idFarmacia;
    private int idProdotto;

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Date getDataReintegro() {
        return dataReintegro;
    }

    public void setDataReintegro(Date dataReintegro) {
        this.dataReintegro = dataReintegro;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

}
