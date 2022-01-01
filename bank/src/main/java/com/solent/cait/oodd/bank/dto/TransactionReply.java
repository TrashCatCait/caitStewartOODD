package com.solent.cait.oodd.bank.dto;

import java.util.Date;

/**
 * 
 * @author caitlyn
 * Reply to send for transactions to the bank client
 */
public class TransactionReply {

    private Integer code;

    private String message;

     private TransactionStatus status;

    private String fromCardNo;

    private String toCardNo;

    private String transactionId;

    private Date timestamp;
    
    private Double amount;

    /**
     * 
     * @return 
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code 
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 
     * @return 
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return 
     */
    public TransactionStatus getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    /**
     * 
     * @return 
     */
    public String getFromCardNo() {
        return fromCardNo;
    }

    /**
     * 
     * @param fromCardNo 
     */
    public void setFromCardNo(String fromCardNo) {
        this.fromCardNo = fromCardNo;
    }

    /**
     * 
     * @return 
     */
    public String getToCardNo() {
        return toCardNo;
    }

    /**
     * 
     * @param toCardNo 
     */
    public void setToCardNo(String toCardNo) {
        this.toCardNo = toCardNo;
    }

    /**
     * 
     * @return 
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 
     * @param transactionId 
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 
     * @return 
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp 
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return 
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount 
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "TransactionReplyMessage{" + "code=" + code + ", message=" + message + ", status=" + status + ", fromCardNo=" + fromCardNo + ", toCardNo=" + toCardNo + ", transactionId=" + transactionId + ", transactionDate=" + timestamp + ", amount=" + amount + '}';
    }
    
}
