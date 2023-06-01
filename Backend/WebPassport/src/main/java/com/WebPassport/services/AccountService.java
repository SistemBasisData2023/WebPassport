package com.WebPassport.services;

import com.WebPassport.entities.AccountEntity;
import com.WebPassport.queries.AccountQuery;
import com.WebPassport.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountService implements AccountRepository {

    public JdbcTemplate jdbcTemplate;
    @Autowired
    public AccountService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AccountEntity> findAllAccount() {
        return jdbcTemplate.query(AccountQuery.FIND_ALL, this::mapRowToAccountEntity);
    }

    @Override
    public AccountEntity findById(int account_id) {
        List<AccountEntity> accountEntityList = new ArrayList<>(jdbcTemplate.query(AccountQuery.FIND_BY_ID, this::mapRowToAccountEntity, account_id));
        return (accountEntityList.isEmpty() ? null : accountEntityList.get(0));
    }

    @Override
    public List<AccountEntity> findByUsername(String username) {
        return jdbcTemplate.query(AccountQuery.FIND_BY_USERNAME, this::mapRowToAccountEntity, username);
    }

    @Override
    public List<AccountEntity> findForLogin(String identity, String password) {
        return jdbcTemplate.query(AccountQuery.FIND_FOR_LOGIN, this::mapRowToAccountEntity, identity,identity, password);
    }

    @Override
    public int save(AccountEntity accountEntity) {
        return jdbcTemplate.update(
                AccountQuery.SAVE, accountEntity.username,
                accountEntity.email, accountEntity.phoneNumber,
                accountEntity.password);
    }

    @Override
    public List<AccountEntity> saveAndReturnAccountEntity(AccountEntity accountEntity) {
        return jdbcTemplate.query(AccountQuery.SAVE_AND_RETURN_ACCOUNT,this::mapRowToAccountEntity ,accountEntity.username,
                accountEntity.email, accountEntity.phoneNumber,
                accountEntity.password);
    }

    @Override
    public AccountEntity updateAndReturnAccountEntity(int account_id, AccountEntity accountEntity) {
        return jdbcTemplate.query(AccountQuery.UPDATE_AND_RETURN_ACCOUNT, this::mapRowToAccountEntity,
                accountEntity.username, accountEntity.email, accountEntity.phoneNumber,
                accountEntity.password, account_id).get(0);
    }

    @Override
    public int delete(int account_id) {
        return jdbcTemplate.update(AccountQuery.DELETE, account_id);
    }

    private AccountEntity mapRowToAccountEntity(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new AccountEntity(resultSet.getInt("account_id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                resultSet.getString("password"));
    }
}
