package com.WebPassport.repositories;

import com.WebPassport.models.Admin;

import java.util.List;

public interface AdminRepository {
    List<Admin> findAllAdmin();
    Admin findById(int account_id);
    List<Admin> findByUsername(String username);
   List<Admin> findForLogin(String identity, String password);
    int save(Admin Admin);
    Admin saveAndReturnAdmin(Admin admin);
    Admin updateAndReturnAdmin(int account_id, Admin admin);
    int delete(int account_id);
}
