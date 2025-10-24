package com.example.demo.domain.utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    @Query("SELECT u FROM Utente u WHERE username = :username")
    Utente findByUsername(String username);
    
}
