package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.conto.Conto;
import com.example.demo.domain.conto.ContoRepository;
import com.example.demo.domain.utente.Utente;
import com.example.demo.domain.utente.UtenteRepository;

import java.util.logging.Logger;

@Service
public class FundomesticUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository userRepository;

    @Autowired
    private ContoRepository contoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Logger logger = Logger.getLogger(getClass().toString());
        logger.info("LOOKING FOR USER:" + username);
        Utente user = userRepository.findByUsername(username);

        if (user == null) {
            logger.info("USER NOT FOUND");
            throw new UsernameNotFoundException("Could not find user");
        } else {
            logger.info("USER FOUND");
        }

        Conto contoPrincipale = contoRepository.getContoPrincipale(user);

        return new FundomesticUserDetails(user, contoPrincipale);
    }

}
