package com.example.demo.domain.transazione;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.conto.Conto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransazioneRepository extends JpaRepository<Transazione, Integer> {

    @Query("""
        SELECT t 
        FROM Transazione t 
        WHERE t.ref_mittente = :conto OR t.ref_destinatario = :conto
        ORDER BY t.data_t DESC
    """)
    List<Transazione> findTransactionsByContoId(Conto conto, Pageable pageable);
}