package com.example.demo.domain.utente;

import jakarta.persistence.*;
import java.util.List;
import com.example.demo.domain.conto.Conto;

@Entity
@Table(name = "Utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_utente;

    @Column(length = 20)
    private String nome;

    @Column(length = 20)
    private String cognome;

    @Column(length = 20)
    private String username;

    @Column(length = 64)
    private String password;

    @OneToMany(mappedBy = "ref_utente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Conto> conti;

    public Utente() {
    }

    public Utente(String name, String surname, String username, String password) {
        this.nome = name;
        this.cognome = surname;
        this.username = username;
        this.password = password;

    }

    // Getter e Setter
    public Integer getId_utente() {
        return id_utente;
    }

    public void setId_utente(Integer id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Conto> getConti() {
        return conti;
    }

    public void setConti(List<Conto> conti) {
        this.conti = conti;
    }

    public String getRole() {
        return "ROLE_USER";
    }
}

