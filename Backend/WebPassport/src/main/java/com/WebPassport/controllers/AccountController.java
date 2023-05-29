package com.WebPassport.controllers;

import com.WebPassport.entities.AccountEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Account;
import com.WebPassport.repositories.AccountRepository;
import com.WebPassport.repositories.AddressRepository;
import com.WebPassport.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    public AccountRepository accountRepository;
    public PersonRepository personRepository;
    public AddressRepository addressRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public AccountController(AccountRepository accountRepository, PersonRepository personRepository,
                             AddressRepository addressRepository, ObjectMapper objectMapper){
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAccount(@RequestParam(required = false) Integer account_id) {
        try {
            List<AccountEntity> accountEntityList = new ArrayList<>();

            accountEntityList.addAll(accountRepository.findAllAccount());

            if (accountEntityList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Account> accountList = new ArrayList<>();
            for(AccountEntity accountEntity : accountEntityList){
                accountList.add(objectMapper.mapToAccount(accountEntity));
            }

            return new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(
            @RequestParam(required = false) String identity,
            @RequestParam String password
    ){
        String encryptedPass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInteger = new BigInteger(1, bytes);
            StringBuilder sb = new StringBuilder(bigInteger.toString(16));
            while(sb.length() < 64){
                sb.insert(0, '0');
            }
            encryptedPass = sb.toString();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException){
            noSuchAlgorithmException.printStackTrace();
        }
        List<AccountEntity> accountEntityList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        try{
            if(identity!=null){
                accountEntityList.addAll(accountRepository.findForLogin(identity,encryptedPass));
                for(AccountEntity accountEntity : accountEntityList){
                    accountList.add(objectMapper.mapToAccount(accountEntity));
                }
            }
            else {
                return new ResponseEntity<>("Username or Email Expected", HttpStatus.METHOD_NOT_ALLOWED);
            }
            if (accountList.isEmpty())
                return new ResponseEntity<>(accountList, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(accountList, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password){

        List<AccountEntity> accountEntityList = new ArrayList<>(accountRepository.findAllAccount());
        List<Account> accountList = new ArrayList<>();
        for(AccountEntity accountEntity : accountEntityList){
            accountList.add(objectMapper.mapToAccount(accountEntity));
        }
        if(!username.isBlank()){
            if(Account.validate(email, phoneNumber, password)){
                for(Account account : accountList){
                    if(account.email.equals(email) || account.username.equals(username)){
                        return new ResponseEntity<>("Email or Username Already Exists.", HttpStatus.BAD_REQUEST);
                    }
                }
                String encryptedPass = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
                    BigInteger bigInteger = new BigInteger(1, bytes);
                    StringBuilder sb = new StringBuilder(bigInteger.toString(16));
                    while(sb.length() < 64){
                        sb.insert(0, '0');
                    }
                    encryptedPass = sb.toString();
                } catch (NoSuchAlgorithmException e){
                    e.printStackTrace();
                }
                try {
                    AccountEntity _accountEntity = new AccountEntity(username, email, phoneNumber, encryptedPass);
                    accountRepository.save(_accountEntity);
                    AccountEntity result = accountRepository.findByUsername(_accountEntity.username).get(0);
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>("REGEX FAIL.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unexpected Error Occurred!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
