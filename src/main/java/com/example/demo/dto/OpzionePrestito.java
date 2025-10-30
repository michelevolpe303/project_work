package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "OpzionePrestito",
    description = "DTO che rappresenta un'opzione di prestito con i dettagli delle rate e dei tassi applicati."
)
public class OpzionePrestito {

    @Schema(description = "Numero totale delle rate previste per il prestito", example = "36")
    private int numeroRate;

    @Schema(description = "Importo della singola rata mensile", example = "289.50")
    private double rataMensile;

    @Schema(description = "Tasso Annuo Nominale (TAN) espresso in percentuale", example = "5.5")
    private double tan;

    @Schema(description = "Tasso Annuo Effettivo Globale (TAEG) espresso in percentuale", example = "5.8")
    private double taeg;

    @Schema(description = "Importo totale dovuto alla fine del piano di rimborso", example = "10422.00")
    private double totaleDovuto;

    public OpzionePrestito(
        int numeroRate,
        double rataMensile,
        double tan,
        double taeg,
        double totaleDovuto
    ) {
        this.numeroRate = numeroRate;
        this.rataMensile = rataMensile;
        this.tan = tan;
        this.taeg = taeg;
        this.totaleDovuto = totaleDovuto;
    }

    public int getNumeroRate() {
        return this.numeroRate;
    }

    public double getRataMensile() {
        return this.rataMensile;
    }

    public double getTan() {
        return this.tan;
    }

    public double getTaeg() {
        return this.taeg;
    }

    public double getTotaleDovuto() {
        return this.totaleDovuto;
    }
}
