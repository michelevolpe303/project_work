package com.example.demo.dto;

public class OpzionePrestito {
    private int numeroRate;
    private double rataMensile;
    private double tan;
    private double taeg;
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
