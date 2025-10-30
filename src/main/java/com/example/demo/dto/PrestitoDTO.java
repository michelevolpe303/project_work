package com.example.demo.dto;

import com.example.demo.domain.prestito.Prestito;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "PrestitoDTO",
    description = "DTO che rappresenta un prestito con i dettagli relativi all'importo, ai tassi e allo stato di rimborso."
)
public class PrestitoDTO {

    @Schema(description = "Identificativo univoco del prestito", example = "42")
    private Integer idPrestito;

    @Schema(description = "Importo totale richiesto per il prestito", example = "10000.00")
    private double importo;

    @Schema(description = "Numero totale di rate previste per il rimborso", example = "36")
    private Integer numeroRate;

    @Schema(description = "Tasso Annuo Nominale (TAN) espresso in percentuale", example = "5.0")
    private Double tan;

    @Schema(description = "Tasso Annuo Effettivo Globale (TAEG) espresso in percentuale", example = "5.3")
    private Double taeg;

    @Schema(description = "Importo della singola rata mensile", example = "300.50")
    private Double rataMensile;

    @Schema(description = "Importo totale già rimborsato sul prestito", example = "1200.00")
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

    // Getter e Setter
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
