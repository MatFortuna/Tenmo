package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {




    List<Account> findAll();

    Account findByUserId(Integer userId);

    Long findAccountIdByUserId(Long userId);

    Account findByAccountId(Long accountId);

    BigDecimal getBalance(Long accountId);

    BigDecimal addToBalance(long accountId, BigDecimal amount);

    BigDecimal subtractFromBalance(long accountId, BigDecimal amount);

    Boolean accountExists(Long accountId);



}
