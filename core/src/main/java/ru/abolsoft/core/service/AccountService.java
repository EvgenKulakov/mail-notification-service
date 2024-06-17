package ru.abolsoft.core.service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.repository.AccountRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = getAccountByLogin(username);
        return new org.springframework.security.core.userdetails.User(
                account.getName(),
                account.getPasswordHash(),
                Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()))
        );
    }

    @Transactional
    public Account getAccountByLogin(String username) {
        Optional<Account> accountOptional = accountRepository.findByName(username);
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException("Account %s not found".formatted(username));
        }
        return accountOptional.get();
    }

    @Transactional
    public Account saveNewAccount(Account account) {

        if (accountRepository.existsAccountByEmail(account.getEmail())) {
            throw new ValidationException("Account with email %s already exists".formatted(account.getEmail()));
        }

        Optional<Account> accountOptional = accountRepository.findByName(account.getName());
        if (accountOptional.isPresent()) {
            throw new ValidationException("Account %s already exists".formatted(account.getName()));
        }
        return accountRepository.save(account);
    }

    @Transactional
    public Account blockAccount(String accountName) {
        Optional<Account> accountOptional = accountRepository.findByName(accountName);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setIsBlock(true);
            return accountRepository.save(account);
        } else {
            throw new ValidationException("Account %s not found".formatted(accountName));
        }
    }

    @Transactional
    public Account unblockAccount(String accountName) {
        Optional<Account> accountOptional = accountRepository.findByName(accountName);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setIsBlock(false);
            return accountRepository.save(account);
        } else {
            throw new ValidationException("Account %s not found".formatted(accountName));
        }
    }
}
