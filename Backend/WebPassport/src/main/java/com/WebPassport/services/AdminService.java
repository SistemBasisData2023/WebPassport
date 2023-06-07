package com.WebPassport.services;

import com.WebPassport.models.Admin;
import com.WebPassport.queries.AdminQuery;
import com.WebPassport.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AdminService implements AdminRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Admin> findAllAdmin() {
        return jdbcTemplate.query(AdminQuery.FIND_ALL, this::mapRowToAdmin);
    }

    @Override
    public Admin findById(int account_id) {
        return jdbcTemplate.query(AdminQuery.FIND_BY_ID, this::mapRowToAdmin, account_id).get(0);
    }

    @Override
    public List<Admin> findByUsername(String username) {
        return null;
    }

    @Override
    public List<Admin> findForLogin(String identity, String password) {
        return jdbcTemplate.query(AdminQuery.FIND_FOR_LOGIN, this::mapRowToAdmin, identity, identity, password);
    }

    @Override
    public int save(Admin Admin) {
        return 0;
    }

    @Override
    public Admin saveAndReturnAdmin(Admin admin) {
        return null;
    }

    @Override
    public Admin updateAndReturnAdmin(int account_id, Admin admin) {
        return null;
    }

    @Override
    public int delete(int account_id) {
        return 0;
    }

    private Admin mapRowToAdmin(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new Admin(resultSet.getInt("admin_id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                resultSet.getString("password"));
    }
}
