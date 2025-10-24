package com.example.demo.web;

import com.example.demo.domain.conto.ContoService;
import com.example.demo.domain.utente.Utente;
import com.example.demo.domain.utente.UtenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    ContoService contoService;

    @Operation(
        summary = "Registra un nuovo utente",
        description = "Crea un nuovo utente con i dati forniti e associa un conto principale con alias scelto."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registrazione avvenuta con successo",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Registrazione avvenuta con successo."))),
        @ApiResponse(responseCode = "409", description = "Nome utente già in uso",
            content = @Content(mediaType = "text/plain",
                schema = @Schema(example = "Il nome utente scelto è già in uso.")))
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Parameter(description = "Nome dell'utente", example = "Mario")
            @RequestParam String nome,

            @Parameter(description = "Cognome dell'utente", example = "Rossi")
            @RequestParam String cognome,

            @Parameter(description = "Username univoco", example = "mrossi")
            @RequestParam String username,

            @Parameter(description = "Password dell'account", example = "password123")
            @RequestParam String password,

            @Parameter(description = "Alias del conto principale", example = "Conto Principale")
            @RequestParam String alias) {
        try {
            Utente u = utenteService.createUtente(
                nome,
                cognome,
                username,
                password
            );

            contoService.createConto(u, alias, true);

            return ResponseEntity.status(HttpStatus.CREATED).body("Registrazione avvenuta con successo.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Il nome utente scelto è già in uso.");
        }
    }
}
