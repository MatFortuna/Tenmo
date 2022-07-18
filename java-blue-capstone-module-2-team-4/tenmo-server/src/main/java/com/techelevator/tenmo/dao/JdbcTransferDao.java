package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private JbdcAccountDao jbdcAccountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JbdcAccountDao jbdcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jbdcAccountDao = jbdcAccountDao;
    }

    @Override
    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;

    }

    @Override
    public Transfer findByTransferId(long transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            return mapRowToTransfer(results);
        }
        return null;
    }

    @Override
    public List<Transfer> findByAccountFrom(long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> findByAccountTo(long accountId) {
        List<Transfer> transfers = new ArrayList<>();
           String sql = "SELECT * FROM transfer WHERE account_from = ? OR account_to = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
            while  (rowSet.next()) {
               transfers.add(mapRowToTransfer(rowSet));

            }   return transfers;
        }


    @Override
    public Integer send(BigDecimal amount, Long accountTo, Long accountFrom) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newTransferId = null;
        if (accountTo == accountFrom) {
            System.out.println("You cannot transfer money to yourself!");
            return null;
        }
            try {
                newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, 2, 2, accountFrom, accountTo, amount);
                jbdcAccountDao.addToBalance(accountTo, amount);
                jbdcAccountDao.subtractFromBalance(accountFrom, amount);
            } catch (DataAccessException e) {
                System.out.println("Invalid account/amount entries!");
                return null;
            }
        return newTransferId;
        }



    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setTransferTypeId(results.getLong("transfer_type_id"));
        transfer.setTransferStatusId(results.getLong("transfer_status_id"));
        transfer.setAccountFrom(results.getLong("account_from"));
        transfer.setAccountTo(results.getLong("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }
}
