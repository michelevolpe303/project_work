package com.example.demo.domain.prestito;

import jakarta.persistence.*;
import com.example.demo.domain.conto.Conto;

@Entity
@Table(name = "Prestito")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestito")
    private Integer idPrestito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ref_conto", nullable = false)
    private Conto refConto;

    @Column(nullable = false)
    private double importo;

    @Column(name = "numero_rate", nullable = false)
    private Integer numeroRate;

    @Column(name = "tan", nullable = false)
    private Double tan;

    @Column(name = "taeg", nullable = false)
    private Double taeg;

    @Column(name = "rata_mensile", nullable = false)
    private Double rataMensile;

    @Column(name = "importo_pagato", nullable = false)
    private double importoPagato;

    // getter e setter in camelCase
    public Integer getIdPrestito() { return idPrestito; }
    public void setIdPrestito(Integer idPrestito) { this.idPrestito = idPrestito; }

    public Conto getRefConto() { return refConto; }
    public void setRefConto(Conto refConto) { this.refConto = refConto; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public Integer getNumeroRate() { return numeroRate; }
    public void setNumeroRate(Integer numeroRate) { this.numeroRate = numeroRate; }

    public Double getTan() { return tan; }
    public void setTan(Double tan) { this.tan = tan; }

    public Double getTaeg() { return taeg; }
    public void setTaeg(Double taeg) { this.taeg = taeg; }

    public Double getRataMensile() { return rataMensile; }
    public void setRataMensile(Double rataMensile) { this.rataMensile = rataMensile; }

    public double getImportoPagato() { return importoPagato; }
    public void setImportoPagato(double importoPagato) { this.importoPagato = importoPagato; }
}
