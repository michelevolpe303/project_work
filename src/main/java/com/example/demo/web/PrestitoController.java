package com.example.demo.web;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.prestito.Prestito;
import com.example.demo.domain.prestito.PrestitoService;
import com.example.demo.dto.OpzionePrestito;
import com.example.demo.dto.PrestitoDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class PrestitoController {
    
    @Autowired
    private PrestitoService prestitoService;

    @Operation(
        summary = "Calcola le opzioni di prestito disponibili",
        description = """
        Il sistema è in grado di calcolare e restituire le diverse opzioni di prestito 
        disponibili in base all'importo richiesto.
        
        La risposta fornisce una lista di oggetti OpzionePrestito, contenente le varie 
        soluzioni proposte (durata, tasso di interesse, numero di rate).
        
        **Calcolo**: 
        - Opzioni disponibili da 12 a 48 rate (intervalli di 6 mesi)
        - TAN fisso al 5%
        - Spese obbligatorie: 2% dell'importo
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opzioni di prestito disponibili",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OpzionePrestito.class)))
    })
    @GetMapping("/api/prestito/opzioni")
    public ResponseEntity<List<OpzionePrestito>> opzioniPrestito(
        @Parameter(description = "Importo per il quale calcolare le opzioni di prestito", example = "10000")
        @RequestParam double importo
    ) {
        return ResponseEntity.ok(prestitoService.opzioniPrestito(importo));
    }
    
    @Operation(
        summary = "Richiede un prestito",
        description = """
        Per inoltrare una nuova richiesta di prestito, l'utente invia i parametri 
        importoRichiesto e numeroRate, che indicano rispettivamente la somma 
        desiderata e il numero di rate per il rimborso.
        
        **Processo**:
        1. Validazione dei parametri
        2. Calcolo delle condizioni del prestito
        3. Creazione del prestito
        4. Accredito immediato dell'importo sul conto
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prestito richiesto con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Prestito richiesto con successo")))
    })
    @PostMapping("/api/prestito/richiedi")
    public ResponseEntity<String> richiediPrestito(
        @Parameter(description = "Importo richiesto", example = "5000")
        @RequestParam int importoRichiesto,

        @Parameter(description = "Numero di rate per il rimborso", example = "24")
        @RequestParam int numeroRate
    ) {
        prestitoService.richiediPrestito(importoRichiesto, numeroRate);        
        return ResponseEntity.status(HttpStatus.CREATED).body("Prestito richiesto con successo");
    }

    @Operation(
        summary = "Lista dei prestiti dell’utente",
        description = """
        Consente di ottenere l'elenco completo dei prestiti associati all'utente autenticato.
        
        Il sistema restituisce una lista di oggetti PrestitoDTO, ciascuno contenente 
        i dettagli relativi a un singolo prestito.
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista dei prestiti",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PrestitoDTO.class)))
    })
    @GetMapping("/api/prestiti")
    public ResponseEntity<List<PrestitoDTO>> getPrestiti() {
        List<Prestito> prestiti = prestitoService.getPrestitiUtente();
        return ResponseEntity.ok(prestiti
                                .stream()
                                .map(PrestitoDTO::new)
                                .toList());
    }

    @Operation(
        summary = "Paga una rata di un prestito",
        description = """
        Per procedere al pagamento di una rata, l'utente specifica l'identificativo 
        numerico del prestito da aggiornare.
        
        **Processo**:
        1. Verifica che il prestito appartenga all'utente
        2. Addebita l'importo della rata dal conto attivo
        3. Aggiorna il totale pagato del prestito
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rata pagata con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Rata pagata con successo"))),
        @ApiResponse(responseCode = "403", description = "Prestito non valido o non appartiene all’utente",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Prestito non valido o non tuo")))
    })
    @PostMapping("/api/prestiti/pagaRata/{idPrestito}")
    public ResponseEntity<String> pagaRata(
        @Parameter(description = "ID del prestito", example = "1")
        @PathVariable int idPrestito
    ) {
        boolean pagato = prestitoService.pagaRata(idPrestito);
        if (pagato) {
            return ResponseEntity.ok("Rata pagata con successo");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Prestito non valido o non tuo");
        }
    }
}
