package ru.abolsoft.core.service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


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
