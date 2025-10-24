package com.example.demo.domain.conto;

import jakarta.persistence.*;
import java.util.List;
import com.example.demo.domain.prestito.Prestito;
import com.example.demo.domain.transazione.Transazione;
import com.example.demo.domain.utente.Utente;;;

@Entity
@Table(name = "Conto")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_conto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ref_utente", nullable = false)
    private Utente ref_utente;

    @Column(length = 30, nullable = false)
    private String alias;

    @Column(nullable = false)
    private double saldo;

    @Column(length = 16, nullable = false)
    private String pan;

    @Column(nullable = false)
    private Integer anno_scadenza;

    @Column(nullable = false)
    private Integer mese_scadenza;

    @Column(length = 3)
    private String cvv;

    @Column(length = 27)
    private String iban;

    @OneToMany(mappedBy = "ref_mittente")
    private List<Transazione> transazioniInviate;

    @OneToMany(mappedBy = "ref_destinatario")
    private List<Transazione> transazioniRicevute;

    @OneToMany(mappedBy = "refConto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestito> prestiti;

    @Column(nullable = false)
    private boolean principale;
    
    public boolean isPrincipale() {
        return principale;
    }
    
    public void setPrincipale(boolean principale) {
        this.principale = principale;
    }

    // Getter e Setter
    public Integer getId_conto() {
        return id_conto;
    }

    public void setId_conto(Integer id_conto) {
        this.id_conto = id_conto;
    }

    public Utente getRef_utente() {
        return ref_utente;
    }

    public void setRef_utente(Utente ref_utente) {
        this.ref_utente = ref_utente;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getAnno_scadenza() {
        return anno_scadenza;
    }

    public void setAnno_scadenza(Integer anno_scadenza) {
        this.anno_scadenza = anno_scadenza;
    }

    public Integer getMese_scadenza() {
        return mese_scadenza;
    }

    public void setMese_scadenza(Integer mese_scadenza) {
        this.mese_scadenza = mese_scadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<Transazione> getTransazioniInviate() {
        return transazioniInviate;
    }

    public void setTransazioniInviate(List<Transazione> transazioniInviate) {
        this.transazioniInviate = transazioniInviate;
    }

    public List<Transazione> getTransazioniRicevute() {
        return transazioniRicevute;
    }

    public void setTransazioniRicevute(List<Transazione> transazioniRicevute) {
        this.transazioniRicevute = transazioniRicevute;
    }

    public List<Prestito> getPrestiti() {
        return prestiti;
    }

    public void setPrestiti(List<Prestito> prestiti) {
        this.prestiti = prestiti;
    }

    public void aggiornaSaldo(double importo) {
        setSaldo(getSaldo() + importo);
    }
}

