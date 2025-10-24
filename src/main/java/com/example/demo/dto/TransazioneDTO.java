package com.example.demo.dto;

import java.time.LocalDateTime;
import com.example.demo.domain.transazione.Transazione;

public class TransazioneDTO {
    private String descrizione;
    private LocalDateTime data;
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
