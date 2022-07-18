package com.techelevator.tenmo.model;

public class TransferType {


    private Long transferTypeId;
    private String transferTypeDesc;

    public TransferType(Long transferTypeId, String transferTypeDesc) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
