package com.WebPassport.controllers;

import com.WebPassport.entities.AccountEntity;
import com.WebPassport.entities.PersonEntity;
import com.WebPassport.models.Account;
import com.WebPassport.models.Person;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    public AccountRepository accountRepository;
    public PersonRepository personRepository;
    public AddressRepository addressRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, PersonRepository personRepository, AddressRepository addressRepository){
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
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
                accountList.add(mapToAccount(accountEntity));
            }

            return new ResponseEntity<>(accountList, HttpStatus.OK);
        } catch (Exception e) {
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
            accountList.add(new Account(
                    accountEntity.account_id, accountEntity.username,
                    accountEntity.email, accountEntity.phoneNumber, accountEntity.password));
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

    public Account mapToAccount(AccountEntity accountEntity){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Account account =  new Account(accountEntity.account_id,
                accountEntity.username, accountEntity.email,
                accountEntity.phoneNumber, accountEntity.password);
        List<PersonEntity> personEntityList = new ArrayList<>(personRepository.findByAccount_id(account.getAccount_id()));

        List<Person> personList = new ArrayList<>();
        for(PersonEntity personEntity : personEntityList){
            try {
                personList.add(new Person(
                                personEntity.person_id,
                                addressRepository.findById(personEntity.address_id).get(0),
                                personEntity.name, personEntity.nik,
                                sdf.parse(personEntity.date_of_birth),
                                personEntity.place_of_birth,
                                Person.Gender.valueOf(personEntity.gender)
                        )
                );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        account.persons = personList;
        return account;
    }

}
