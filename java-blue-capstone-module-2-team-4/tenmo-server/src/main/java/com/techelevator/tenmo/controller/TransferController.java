package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public List<Transfer> transfersList() {
        return transferDao.findAll();
    }

    @RequestMapping(value = "/transfer/log", method = RequestMethod.GET)
    public List<Transfer> transfer(Principal principal) {
        String currentUser = principal.getName();

        Long userId = (userDao.findIdByUsername(currentUser));
        Long accountId = (accountDao.findAccountIdByUserId(userId));

        return transferDao.findByAccountTo(accountId);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public boolean transferSend(@Valid @RequestBody Transfer newTransfer, Principal principal) {
        String currentUser = principal.getName();
        Long userId = userDao.findIdByUsername(currentUser);
        Long accountId = accountDao.findAccountIdByUserId(userId);
        if (newTransfer.getAccountTo().equals(userId)) {
            return false;
        } else if (newTransfer.getAmount().doubleValue() > 0 && newTransfer.getAmount().doubleValue() <= accountDao.getBalance(accountId).doubleValue()
                && accountDao.accountExists(newTransfer.getAccountTo())) {
            transferDao.send(newTransfer.getAmount(), newTransfer.getAccountTo(), accountId);
            return true;
        } else
            return false;
    }


}


