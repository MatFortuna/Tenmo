package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JbdcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JbdcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAll() {
          List<Account> accounts = new ArrayList<>();
            String sql = "SELECT account_id, username FROM account JOIN tenmo_user USING (user_id);";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
                Account account = mapRowToAccountForDisplay(results);
                accounts.add(account);
            }
            return accounts;
        }

    @Override
    public Account findByUserId(Integer userId) throws  UsernameNotFoundException {
            String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            if (rowSet.next()){
                return mapRowToAccount(rowSet);
            }
            throw new UsernameNotFoundException("User " + userId + " was not found.");
        }

    @Override
    public Long findAccountIdByUserId(Long userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()){
            return mapRowToAccount(rowSet).getAccountId();
        }
        throw new UsernameNotFoundException("User " + userId + " was not found.");
    }


    @Override
    public Account findByAccountId(Long accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + accountId + " was not found.");
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        if (rowSet.next()) {
            return mapRowToAccount(rowSet).getBalance();
        }  return null;
    }

    @Override
    public BigDecimal addToBalance(long accountId, BigDecimal amount) {
        BigDecimal updatedBalance = getBalance(accountId).add(amount);
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        try {
            jdbcTemplate.update(sql, updatedBalance, accountId);
            return updatedBalance;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal subtractFromBalance(long accountId, BigDecimal amount) {
        BigDecimal updatedBalance = getBalance(accountId).subtract(amount);
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        try {
            jdbcTemplate.update(sql, updatedBalance, accountId);
            return updatedBalance;
        } catch (DataAccessException e) {
            return null;
        }
    }
        @Override
       public Boolean accountExists(Long accountId) {
            List<Account> accounts = new ArrayList<>();
            String sql = "SELECT account_id, username FROM account JOIN tenmo_user USING (user_id);";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Account account = mapRowToAccountForDisplay(results);
                accounts.add(account);
        if (account.getAccountId().equals(accountId)){
            return true;
    }

    }   return false;
        }
    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserID(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

    private Account mapRowToAccountForDisplay(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUsername(rs.getString("username"));
        return account;
    }
}
