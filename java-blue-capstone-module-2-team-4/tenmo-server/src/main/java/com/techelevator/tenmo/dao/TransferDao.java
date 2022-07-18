package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    public List<Transfer> findAll();

    public Transfer findByTransferId(long transferId);

    public List<Transfer> findByAccountFrom(long accountId);

    public  List<Transfer> findByAccountTo(long accountId);

    public Integer send(BigDecimal amount, Long accountTo, Long accountFrom);
}
