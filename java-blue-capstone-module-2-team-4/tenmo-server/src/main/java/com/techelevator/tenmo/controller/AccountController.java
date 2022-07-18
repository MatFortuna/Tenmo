package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JbdcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")

@RestController
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;



    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;

    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
        List<Account> accounts() {
               return accountDao.findAll();
        }

    @RequestMapping(value = "/account/balance", method = RequestMethod.GET)
    public BigDecimal account( Principal principal) {
              String currentUser = principal.getName();

            Long userId = (userDao.findIdByUsername(currentUser));

              return accountDao.getBalance(accountDao.findAccountIdByUserId(userId));
    }



}
