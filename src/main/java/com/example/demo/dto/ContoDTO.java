package com.example.demo.dto;

import com.example.demo.domain.conto.Conto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ContoDTO",
    description = "Data Transfer Object che rappresenta un conto bancario dell'utente"
)
public class ContoDTO {

    @Schema(description = "Identificativo univoco del conto", example = "101")
    private Integer idConto;

    @Schema(description = "Alias o nome personalizzato assegnato al conto", example = "Conto Principale")
    private String alias;

    @Schema(description = "Saldo corrente del conto", example = "1530.75")
    private double saldo;

    @Schema(description = "Numero PAN della carta associata al conto (parzialmente mascherato)", example = "1234-5678-****-9012")
    private String pan;

    @Schema(description = "Anno di scadenza della carta", example = "2027")
    private Integer annoScadenza;

    @Schema(description = "Mese di scadenza della carta", example = "12")
    private Integer meseScadenza;

    @Schema(description = "Codice di sicurezza della carta (CVV)", example = "123")
    private String cvv;

    @Schema(description = "IBAN del conto", example = "IT60X0542811101000000123456")
    private String iban;

    @Schema(description = "Indica se il conto è il principale per l'utente", example = "true")
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
    public Integer getIdConto() { return idConto; }
    public String getAlias() { return alias; }
    public double getSaldo() { return saldo; }
    public String getPan() { return pan; }
    public Integer getAnnoScadenza() { return annoScadenza; }
    public Integer getMeseScadenza() { return meseScadenza; }
    public String getCvv() { return cvv; }
    public String getIban() { return iban; }
    public boolean isPrincipale() { return principale; }
}
