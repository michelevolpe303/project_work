package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.conto.ContoService;
import com.example.demo.dto.ContoDTO;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ContoController {

    @Autowired
    private ContoService contoService;

    @Operation(
        summary = "Crea un nuovo conto",
        description = """        
        La creazione di ulteriori conti è possibile tramite questo endpoint.
        L'operazione richiede l'autenticazione dell'utente mediante username e password, 
        oltre all'indicazione dell'alias per il nuovo conto.
        
        **Esempio**: Creazione di un "Conto Risparmi"
        
        **Requisiti**:
        - Utente deve essere autenticato tramite sessione
        - Username e password devono corrispondere all'utente autenticato
        - L'alias deve essere univoco nel sistema
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conto creato con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Conto creato"))),
        @ApiResponse(responseCode = "403", description = "Utente non autorizzato a creare il conto",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Non sei autorizzato a creare il conto")))
    })
    @PostMapping("/api/conto/crea")
    public ResponseEntity<String> nuovoConto(
        @Parameter(description = "Username dell'utente", example = "mrossi")
        @RequestParam String username,

        @Parameter(description = "Password dell'utente", example = "password123")
        @RequestParam String password,

        @Parameter(description = "Alias del nuovo conto", example = "Conto Risparmi")
        @RequestParam String alias
    ) {
        try {      
            contoService.createConto(username, password, alias);
            return ResponseEntity.status(HttpStatus.CREATED).body("Conto creato");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato a creare il conto");
        }
    }

    @Operation(
        summary = "Cambia conto attivo",
        description = """
        Permette di selezionare un conto come attivo tramite alias.

        **Comportamento**: Il conto selezionato diventa quello attivo per tutte le 
        operazioni successive (transazioni, prestiti, ecc.).

        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conto cambiato con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Conto cambiato"))),
        @ApiResponse(responseCode = "403", description = "Non autorizzato ad accedere al conto",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Non sei autorizzato ad accedere al conto")))
    })
    @GetMapping("/api/conto/cambia")
    public ResponseEntity<String> cambiaConto(
        @Parameter(description = "Alias del conto da attivare", example = "Conto Principale")
        @RequestParam String alias
    ) {
        try {
            contoService.cambiaConto(alias);
            return ResponseEntity.ok().body("Conto cambiato");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato ad accedere al conto");
        }
    }

    @Operation(
        summary = "Recupera il conto attivo",
        description = """
        Consente di ottenere i dettagli relativi al conto attualmente attivo 
        per l'utente autenticato.
        
        La risposta restituisce un oggetto ContoDTO contenente tutte le 
        informazioni principali del conto.
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conto attivo recuperato con successo",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ContoDTO.class))),
        @ApiResponse(responseCode = "403", description = "Non autorizzato ad accedere al conto attivo")
    })
    @GetMapping("/api/conto")
    public ResponseEntity<ContoDTO> getConto() {
        return ResponseEntity.ok().body(new ContoDTO(contoService.getContoFromAuth()));
    }
}
