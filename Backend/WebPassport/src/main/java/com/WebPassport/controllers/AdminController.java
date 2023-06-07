package com.WebPassport.controllers;

import com.WebPassport.models.Admin;
import com.WebPassport.repositories.AdminRepository;
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
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(
            @RequestParam String identity,
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

        List<Admin> adminList = new ArrayList<>();
        try{
            if(identity!=null){
                adminList.addAll(adminRepository.findForLogin(identity, encryptedPass));
            }
            else {
                return new ResponseEntity<>("Username or Email Expected", HttpStatus.METHOD_NOT_ALLOWED);
            }
            if (adminList.isEmpty())
                return new ResponseEntity<>(adminList, HttpStatus.NO_CONTENT);

            Admin admin = adminList.get(0);

            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
