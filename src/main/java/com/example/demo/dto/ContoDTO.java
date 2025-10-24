package com.example.demo.dto;

import com.example.demo.domain.conto.Conto;

public class ContoDTO {

    private Integer idConto;
    private String alias;
    private double saldo;
    private String pan;
    private Integer annoScadenza;
    private Integer meseScadenza;
    private String cvv;
    private String iban;
    private boolean principale;

    // Costruttore vuoto necessario per Jackson
    public ContoDTO() {}

    // Costruttore che riceve l'entità Conto
    public ContoDTO(Conto conto) {
        this.idConto = conto.getId_conto();
        this.alias = conto.getAlias();
        this.saldo = conto.getSaldo();
        this.pan = conto.getPan();
        this.annoScadenza = conto.getAnno_scadenza();
        this.meseScadenza = conto.getMese_scadenza();
        this.cvv = conto.getCvv();
        this.iban = conto.getIban();
        this.principale = conto.isPrincipale();
    }

    // Getter e Setter
    public Integer getIdConto() {
        return idConto;
    }

    public String getAlias() {
        return alias;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getPan() {
        return pan;
    }

    public Integer getAnnoScadenza() {
        return annoScadenza;
    }

    public Integer getMeseScadenza() {
        return meseScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public String getIban() {
        return iban;
    }

    public boolean isPrincipale() {
        return principale;
    }
}

