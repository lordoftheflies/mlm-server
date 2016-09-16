/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.security.service;

import hu.cherubits.wonderjam.exceptions.CredentialsException;
import hu.cherubits.wonderjam.persistence.dal.AccountRepository;
import hu.cherubits.wonderjam.persistence.entities.AccountEntity;
import hu.cherubits.wonderjam.security.model.SessionDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author lordoftheflies
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private static final Logger LOG = Logger.getLogger(JpaUserDetailsService.class.getName());

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SessionDto result = new SessionDto();
        AccountEntity accountEntity = accountRepository.findByEmail(username);
        if (accountEntity == null) {
            LOG.log(Level.INFO, "Sign-in failed for {0}", username);
            throw new UsernameNotFoundException("Account not found.");
        } else {
            LOG.log(Level.INFO, "Sign-in user {0}", username);
            return accountEntity;
        }
    }

}
