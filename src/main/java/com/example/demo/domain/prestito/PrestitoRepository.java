package com.example.demo.domain.prestito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.conto.Conto;

public interface PrestitoRepository extends JpaRepository<Prestito, Integer> {

    List<Prestito> findByRefConto(Conto conto);

}