package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerService(Account account){
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if(optionalAccount.isPresent()){
            return null;
        }
        else {
            return accountRepository.save(account);
        }
    }

    public Account loginService(Account account){
            Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
            if(optionalAccount.isEmpty() ){
                return null;
            }
            else if
                (!optionalAccount.get().getPassword().equals(account.getPassword())){
                    return null;
            }
            else {
                return optionalAccount.get();
            }    
        }
    

}