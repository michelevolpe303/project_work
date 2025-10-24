package com.example.demo.dto;

import com.example.demo.domain.prestito.Prestito;

public class PrestitoDTO {

    private Integer idPrestito;
    private double importo;
    private Integer numeroRate;
    private Double tan;
    private Double taeg;
    private Double rataMensile;
    private double importoPagato;

    // Costruttore che prende in input un Prestito
    public PrestitoDTO(Prestito prestito) {
        this.idPrestito = prestito.getIdPrestito();
        this.importo = prestito.getImporto();
        this.numeroRate = prestito.getNumeroRate();
        this.tan = prestito.getTan();
        this.taeg = prestito.getTaeg();
        this.rataMensile = prestito.getRataMensile();
        this.importoPagato = prestito.getImportoPagato();
    }

    // Getter e setter
    public Integer getIdPrestito() {
        return idPrestito;
    }

    public void setIdPrestito(Integer idPrestito) {
        this.idPrestito = idPrestito;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public Integer getNumeroRate() {
        return numeroRate;
    }

    public void setNumeroRate(Integer numeroRate) {
        this.numeroRate = numeroRate;
    }

    public Double getTan() {
        return tan;
    }

    public void setTan(Double tan) {
        this.tan = tan;
    }

    public Double getTaeg() {
        return taeg;
    }

    public void setTaeg(Double taeg) {
        this.taeg = taeg;
    }

    public Double getRataMensile() {
        return rataMensile;
    }

    public void setRataMensile(Double rataMensile) {
        this.rataMensile = rataMensile;
    }

    public double getImportoPagato() {
        return importoPagato;
    }

    public void setImportoPagato(double importoPagato) {
        this.importoPagato = importoPagato;
    }
}
