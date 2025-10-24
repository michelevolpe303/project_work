package com.example.demo.domain.transazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.conto.Conto;
import com.example.demo.domain.conto.ContoRepository;
import com.example.demo.domain.conto.ContoService;
import com.example.demo.dto.TransazioneDTO;
import com.example.demo.security.FundomesticUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransazioneService {
    @Autowired
    private TransazioneRepository transazioneRepository;

    @Autowired
    private ContoService contoService;

    @Autowired
    private ContoRepository contoRepository;


    public static final int PAGE_SIZE = 10;

    public List<TransazioneDTO> getTransazioni(Integer currentPage) {
        // Ottengo l'id dell'utente dal SecurityContext
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conto conto = ( (FundomesticUserDetails) user ).getConto();

        //Creo la PageRequest per ottenere le transazioni non ancora ricevute
        PageRequest request = PageRequest.of(currentPage, PAGE_SIZE);

        List<Transazione> transazioni = transazioneRepository.findTransactionsByContoId(conto, request);

        List<TransazioneDTO> transazioniDTO = new ArrayList<>();
        for (Transazione transazione : transazioni) {
            boolean isMittente = transazione.getRef_mittente().getId_conto() == conto.getId_conto();
            transazioniDTO.add(
                new TransazioneDTO(transazione, isMittente)
            );
        }

        return transazioniDTO;
    }

    public Transazione inviaDenaro(String iban_o_alias, double importo, String descrizione) {
        // Ottengo l'id del mittente dal SecurityContext
        FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conto mittente = user.getConto();

        // Ottengo l'id del destinatario
        Conto destinatario = contoService.getContoFromIbanOrAlias(iban_o_alias);
        if (destinatario == null || destinatario.getId_conto().equals(mittente.getId_conto())) {
            throw new RuntimeException();
        }

        //Creo la nuova entità Transazione
        Transazione t = new Transazione(
            mittente,
            destinatario,
            descrizione,
            importo
        );

        mittente.aggiornaSaldo(-importo);
        mittente = contoRepository.save(mittente);
        user.setConto(mittente);

        destinatario.aggiornaSaldo(importo);
        contoRepository.save(destinatario);

        return transazioneRepository.save(t);
    }

}
