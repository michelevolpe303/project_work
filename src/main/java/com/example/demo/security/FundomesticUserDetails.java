package com.example.demo.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.conto.Conto;
import com.example.demo.domain.utente.Utente;

public class FundomesticUserDetails implements UserDetails {

    private final Utente utente;
    private Conto contoInUso;

    private List<String> aliasList;


    public FundomesticUserDetails(Utente utente, Conto conto) {
        this.utente = utente;
        this.contoInUso = conto;
        this.aliasList = utente.getConti()
                            .stream()
                            .map(Conto::getAlias)
                            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(utente.getRole());
        return List.of(authority);
    }

    public Utente getUser() {
        return utente;
    }

    public Conto getConto() {
        return contoInUso;
    }

    public String getAliasCorrente() {
        return contoInUso.getAlias();
    }

    public String getPanSpaced() {
        String pan = contoInUso.getPan();

        if (pan == null || pan.length() < 4) {
            return pan; // or return "" if you prefer
        }
        StringBuilder spaced = new StringBuilder();
        for (int i = 0; i < pan.length(); i++) {
            spaced.append(pan.charAt(i));
            if ((i + 1) % 4 == 0 && i != pan.length() - 1) {
                spaced.append(" ");
            }
        }
        return spaced.toString();
    }

    public String getScadenza() {
        int anno = contoInUso.getAnno_scadenza() % 100;
        int mese = contoInUso.getMese_scadenza();
        return String.format("%02d", mese) + "/" + String.format("%02d", anno);
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public void updateAliasList(String alias) {
        aliasList.add(alias);
    }   

    public void setConto(Conto conto) {
        this.contoInUso = conto;
    }

    public int getSaldo() {
        return 0;
    }

    @Override
    public String getPassword() {
        return utente.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return utente.getUsername();
    }

    public int getId_utente() {
        return utente.getId_utente();
    }

}
