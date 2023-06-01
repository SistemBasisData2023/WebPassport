package com.WebPassport.controllers;

import com.WebPassport.entities.AccountEntity;
import com.WebPassport.entities.PersonEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Account;
import com.WebPassport.models.Address;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
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
                    AccountEntity account_id = accountRepository.saveAndReturnAccountEntity(_accountEntity).get(0);
                    return new ResponseEntity<>(account_id, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>("REGEX FAIL.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unexpected Error Occurred!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{account_id}/addperson")
    public ResponseEntity<?> addPerson(@PathVariable int account_id,
                                       @RequestParam String name,
                                       @RequestParam String nik,
                                       @RequestParam String date_of_birth,
                                       @RequestParam String place_of_birth,
                                       @RequestParam String gender,
                                       @RequestParam String address_line,
                                       @RequestParam String subDistrict,
                                       @RequestParam String city,
                                       @RequestParam String province,
                                       @RequestParam String postCode){

        try {
            int address_id = addressRepository.saveAndReturnId(new Address(address_line, subDistrict, city, province, postCode));
            int rows = personRepository.save(new PersonEntity(account_id,address_id,name,nik,date_of_birth,place_of_birth,gender));

            AccountEntity _accountEntity = accountRepository.findById(account_id);
            Account _account = objectMapper.mapToAccount(_accountEntity);

            return new ResponseEntity<>(_account, HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{account_id}/update")
    public ResponseEntity<?> updateAccount(
            @PathVariable int account_id, @RequestParam(required = false) String username,
            @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String password){
        try{
            String encryptedPass = null;
            if(password != null){
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
            }
            AccountEntity currentAccount = accountRepository.findById(account_id);
            if (currentAccount == null){
                return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
            }
            AccountEntity accountEntity = new AccountEntity(
                    (username == null ? currentAccount.username : username),
                    (email == null ? currentAccount.email : email),
                    (phoneNumber == null ? currentAccount.phoneNumber : phoneNumber),
                    (password == null ? currentAccount.password : encryptedPass));
            AccountEntity _accountEntity = accountRepository.updateAndReturnAccountEntity(account_id, accountEntity);
            return  new ResponseEntity<>(_accountEntity, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{account_id}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable int account_id){
        try{
            List<PersonEntity> personEntityList = personRepository.findByAccount_id(account_id);
            for (PersonEntity personEntity : personEntityList){
                addressRepository.delete(personEntity.address_id);
            }
            int personRow = personRepository.deleteByAccount_id(account_id);
            int rows = accountRepository.delete(account_id);
            return new ResponseEntity<>("Delete "+rows+ " account with "+personRow+" person assigned", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
