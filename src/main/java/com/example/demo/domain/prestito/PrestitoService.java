package com.example.demo.domain.prestito;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.conto.Conto;
import com.example.demo.domain.conto.ContoRepository;
import com.example.demo.dto.OpzionePrestito;
import com.example.demo.security.FundomesticUserDetails;

import jakarta.transaction.Transactional;

@Service
public class PrestitoService {
    @Autowired
    ContoRepository contoRepository;

    @Autowired
    PrestitoRepository prestitoRepository;


    // TAN fisso al 5%
    private static final double TAN = 0.05;

    public List<OpzionePrestito> opzioniPrestito(double importo) {
        // Il numero di rate va da un minimo di 12 (1 anno)
        // a un massimo di 48 (4 anni) ad intervalli di 6

        List<OpzionePrestito> listOpzioni = new ArrayList<>();
        for (int numeroRate = 12; numeroRate <= 48; numeroRate += 6) {
            listOpzioni.add(getOpzionePrestito(importo, numeroRate));
        }

        return listOpzioni;
    }

    public OpzionePrestito getOpzionePrestito(double importo, int numeroRate) {
        // Spese obbligatorie pari al 2% dell'importo richiesto
        double speseObbligatorie = importo * 0.02;

        // Rata mensile calcolata con ammortamento alla francese
        double i = TAN / 12;
        double x = Math.pow(1 + i, numeroRate);
        double rataMensile = importo * i * x / (x - 1);

        // Calcolo del totale dovuto. Si aggiungono spese obbligatorie
        // pari al 2% del capitale
        double totaleDovuto = rataMensile * numeroRate + speseObbligatorie;

        // Calcolo del taeg
        double taeg = totaleDovuto / importo - 1; 

        return new OpzionePrestito(numeroRate, rataMensile, TAN, taeg, totaleDovuto);
    }

    @Transactional
    public void richiediPrestito(int importo, int numeroRate) {
        OpzionePrestito o = getOpzionePrestito(importo, numeroRate);

        FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) { // Utente non autenticato
            throw new RuntimeException();
        }

        Prestito prestito = new Prestito();
        prestito.setImporto(importo);
        prestito.setNumeroRate(numeroRate);
        prestito.setTan(o.getTan());
        prestito.setTaeg(o.getTaeg());
        prestito.setRataMensile(o.getRataMensile());
        prestito.setImportoPagato(0);

        Conto conto = user.getConto();
        conto.aggiornaSaldo(importo);
        contoRepository.save(conto);

        prestito.setRefConto(conto);
        prestitoRepository.save(prestito);
    }

    public List<Prestito> getPrestitiUtente() {
        FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conto conto = user.getConto();
        return prestitoRepository.findByRefConto(conto);
    }

    // Paga una rata di un prestito
    @Transactional
    public boolean pagaRata(int idPrestito) {
        FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Prestito prestito = prestitoRepository.findById(idPrestito).orElse(null);

        if (prestito == null || prestito.getRefConto().getId_conto() != user.getConto().getId_conto()) {
            return false; // prestito non valido o non appartiene all'utente
        }

        prestito.setImportoPagato(prestito.getImportoPagato() + prestito.getRataMensile());
        prestitoRepository.save(prestito);

        Conto conto = user.getConto();
        conto.aggiornaSaldo(-prestito.getRataMensile());
        contoRepository.save(conto);
        return true;
    }
}
