package com.example.demo.domain.conto;

import java.util.Random;
import java.util.regex.Pattern;
import java.time.Year;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.utente.Utente;
import com.example.demo.domain.utente.UtenteRepository;
import com.example.demo.security.FundomesticUserDetails;

@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern IBAN_PATTERN = Pattern.compile("^[A-Z]{2}\\d{2}[A-Z0-9]{12}$");

    public Conto getContoFromIbanOrAlias(String iban_o_alias) {
        Boolean isIban = IBAN_PATTERN.matcher(iban_o_alias).find();
        Conto conto = null;
        if (isIban) {
            conto = contoRepository.findContoByIban(iban_o_alias);
        } else {
            conto = contoRepository.findContoByAlias(iban_o_alias);
        }

        return conto;
    }

    public Conto getContoFromAuth() {
        // Ottengo l'id dell'utente dal SecurityContext
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conto conto = ( (FundomesticUserDetails) user ).getConto();

        return conto;
    }

    public void cambiaConto(String alias) {
        FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getAliasList().contains(alias)) {
            user.setConto(contoRepository.findContoByAlias(alias));
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    public void createConto(String username, String rawPassword, String alias) {
        Utente u = utenteRepository.findByUsername(username);
        if (u == null) {    // Username non trovato
            throw new RuntimeException("Credenziali non valide");
        }

        if (contoRepository.findContoByAlias(alias) != null) {  // Alias già esistente
            throw new RuntimeException("Alias non disponibile");
        }

        String encodedPassword = u.getPassword();
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            createConto(u, alias, false);
            FundomesticUserDetails user = (FundomesticUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.updateAliasList(alias);
        } else {
            throw new RuntimeException("Credenziali non valide");
        }

    }

    public void createConto(Utente u, String alias, boolean principale) {
        Conto c = new Conto();

        String iban = "";
        do {
            iban = generateIban();
        } while (contoRepository.findContoByIban(iban) != null);

        String pan = "";
        do {
            pan = generatePan();
        } while (contoRepository.findContoByPan(pan) != null);
        
        c.setAlias(alias);
        c.setRef_utente(u);
        c.setIban(iban);
        c.setPan(pan);
        c.setCvv(generateCvv());
        c.setAnno_scadenza(Year.now().getValue() + 10);
        c.setMese_scadenza(1);
        c.setPrincipale(principale);
        c.setSaldo(0);

        contoRepository.save(c);
    }

    private String generateIban() {
        String countryCode = "IT";
        String checkDigits = "00"; // fissi o casuali
        String abi = "05428";      // codice banca fittizio
        String cab = "11101";      // codice filiale fittizio
        String conto = String.format("%010d", new Random().nextInt(1_000_000_000)); // numero conto casuale

        return countryCode + checkDigits + "X" + abi + cab + conto;
    }

    private String generatePan() {
        // Prefisso tipico Visa
        String prefix = "4000";
        StringBuilder pan = new StringBuilder(prefix);
        Random rnd = new Random();
    
        // Aggiungi cifre casuali fino a 16
        while (pan.length() < 16) {
            pan.append(rnd.nextInt(10));
        }
        return pan.toString();
    }
    
    private String generateCvv() {
        Random rnd = new Random();
        return String.format("%03d", rnd.nextInt(1000)); // 000–999
    }
}
