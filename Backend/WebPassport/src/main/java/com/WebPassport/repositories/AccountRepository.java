package com.WebPassport.repositories;


import com.WebPassport.entities.AccountEntity;
import com.WebPassport.models.Account;

import java.util.List;

public interface AccountRepository{
    List<AccountEntity> findAllAccount();
    List<AccountEntity> findById(int account_id);
    List<AccountEntity> findByUsername(String username);
    List<AccountEntity> findForLogin(String identity, String password);
    int save(AccountEntity accountEntity);
}
