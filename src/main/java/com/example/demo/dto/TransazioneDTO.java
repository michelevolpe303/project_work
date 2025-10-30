package com.example.demo.dto;

import java.time.LocalDateTime;
import com.example.demo.domain.transazione.Transazione;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "TransazioneDTO",
    description = "DTO che rappresenta una transazione bancaria, con importo positivo o negativo a seconda che l'utente sia mittente o destinatario."
)
public class TransazioneDTO {

    @Schema(description = "Descrizione o causale della transazione", example = "Pagamento bolletta luce")
    private String descrizione;

    @Schema(description = "Data e ora in cui la transazione è stata effettuata", example = "2025-10-29T14:30:00")
    private LocalDateTime data;

    @Schema(description = "Importo della transazione. Negativo se l'utente è mittente, positivo se destinatario.", example = "-75.50")
    private double importo;

    public TransazioneDTO(Transazione t, boolean mittente) {
        this.descrizione = t.getDescrizione();
        this.data = t.getData();
        this.importo = t.getImporto();
        if (mittente) {
            this.importo *= -1;
        }
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getImporto() {
        return importo;
    }
}
