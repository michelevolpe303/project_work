package com.example.demo.domain.utente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente createUtente(
        String name, 
        String surname, 
        String username, 
        String password
    ) {
        if (utenteRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username già in uso!");
        }

        Utente user = new Utente(
            name,
            surname,
            username,
            passwordEncoder.encode(password)
        );

        return utenteRepository.save(user);
    }
    
}
