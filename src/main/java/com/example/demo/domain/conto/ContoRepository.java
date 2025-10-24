package com.example.demo.domain.conto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.utente.Utente;

public interface ContoRepository extends JpaRepository<Conto, Integer> {

    Conto findContoByIban(String iban);

    Conto findContoByAlias(String alias);

    @Query("""
        SELECT c
        FROM Conto c
        WHERE c.ref_utente = :user
        AND c.principale = true
    """)
    Conto getContoPrincipale(Utente user);

    Conto findContoByPan(String pan);
}
