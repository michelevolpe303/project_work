package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.transazione.TransazioneService;
import com.example.demo.dto.TransazioneDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TransazioneController {
    
    @Autowired
    private TransazioneService transazioneService;

    @Operation(
        summary = "Recupera le transazioni",
        description = "Restituisce una lista di transazioni per l’utente autenticato, paginata in base al parametro `pagina`."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transazioni recuperate con successo",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TransazioneDTO.class))),
        @ApiResponse(responseCode = "404", description = "Nessuna transazione trovata",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TransazioneDTO.class)))
    })
    @GetMapping("/api/transazioni/ottieni")
    public ResponseEntity<List<TransazioneDTO>> getTransazioni(
        @Parameter(description = "Numero della pagina di transazioni", example = "1")
        @RequestParam int pagina
    ) {
        List<TransazioneDTO> transactions = transazioneService.getTransazioni(pagina);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactions);
        } else {        
            return ResponseEntity.ok().body(transactions);
        }
    }

    @Operation(
        summary = "Invia denaro a un altro utente",
        description = "Permette all’utente autenticato di inviare denaro a un altro utente specificando destinatario, importo e descrizione."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transazione eseguita con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Transazione eseguita con successo"))),
        @ApiResponse(responseCode = "400", description = "Parametri non validi",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Parametri della richiesta non validi")))
    })
    @PostMapping("/api/inviaDenaro")
    public ResponseEntity<String> inviaDenaro(
        @Parameter(description = "Username del destinatario", example = "mrossi")
        @RequestParam String destinatario,

        @Parameter(description = "Importo da inviare", example = "150.50")
        @RequestParam double importo,

        @Parameter(description = "Descrizione della transazione", example = "Pagamento cena")
        @RequestParam String descrizione
    ) {
        try {        
            transazioneService.inviaDenaro(destinatario, importo, descrizione);
            return ResponseEntity.status(HttpStatus.CREATED).body("Transazione eseguita con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri della richiesta non validi");
        }
    }
}
