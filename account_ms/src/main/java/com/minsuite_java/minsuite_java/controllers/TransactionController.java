package com.minsuite_java.minsuite_java.controllers;

import com.minsuite_java.minsuite_java.exceptions.AccountNotFoundException;
import com.minsuite_java.minsuite_java.exceptions.InsufficientBalanceException;
import com.minsuite_java.minsuite_java.models.Account;
import com.minsuite_java.minsuite_java.models.Transaction;
import com.minsuite_java.minsuite_java.repositories.AccountRepository;
import com.minsuite_java.minsuite_java.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TransactionController {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public TransactionController(AccountRepository accountRepository,
                                 TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    @PostMapping("/transactions")
    Transaction newTransaction(@RequestBody Transaction transaction){
        Account accountOrigin =
                accountRepository.findById(transaction.getUsernameOrigin()).orElse(null);
        Account accountDestinity=
                accountRepository.findById(transaction.getUsernameDestiny()).orElse(null);
        if (accountOrigin == null)
            throw new AccountNotFoundException("No se encontro una cuenta con el username:" + transaction.getUsernameOrigin());
        if (accountDestinity == null)
            throw new AccountNotFoundException("No se encontro una cuenta con el username:" + transaction.getUsernameDestiny());
        if(accountOrigin.getBalance() < transaction.getValue())
            throw new InsufficientBalanceException("Saldo Insuficiente");
        accountOrigin.setBalance(accountOrigin.getBalance() - transaction.getValue());
        accountOrigin.setLastChange(new Date());
        accountRepository.save(accountOrigin);
        accountDestinity.setBalance(accountDestinity.getBalance() +
                transaction.getValue());
        accountDestinity.setLastChange(new Date());
        accountRepository.save(accountDestinity);
        transaction.setDate(new Date());
        return transactionRepository.save(transaction);
    }
}


