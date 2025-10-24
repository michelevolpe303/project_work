package com.example.demo.domain.transazione;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.demo.domain.conto.Conto;

@Entity
@Table(name = "Transazione")
public class Transazione {

    public Transazione(
        Conto mittente,
        Conto destinatario,
        String descrizione,
        double importo
    ) {
        setRef_destinatario(destinatario);
        setRef_mittente(mittente);
        setDescrizione(descrizione);
        setImporto(importo);
        setData(LocalDateTime.now());
    }

    public Transazione() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_transazione;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ref_mittente", nullable = false)
    private Conto ref_mittente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ref_destinatario", nullable = false)
    private Conto ref_destinatario;

    @Column(length = 255)
    private String descrizione;

    @Column(nullable = false)
    private double importo;

    @Column(nullable = false)
    private LocalDateTime data_t;

    // Getter e Setter
    public Integer getId_transazione() {
        return id_transazione;
    }

    public void setId_transazione(Integer id_transazione) {
        this.id_transazione = id_transazione;
    }

    public Conto getRef_mittente() {
        return ref_mittente;
    }

    public void setRef_mittente(Conto ref_mittente) {
        this.ref_mittente = ref_mittente;
    }

    public Conto getRef_destinatario() {
        return ref_destinatario;
    }

    public void setRef_destinatario(Conto ref_destinatario) {
        this.ref_destinatario = ref_destinatario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public LocalDateTime getData() {
        return data_t;
    }

    public void setData(LocalDateTime data) {
        this.data_t = data;
    }
}
