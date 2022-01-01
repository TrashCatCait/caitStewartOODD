package com.solent.cait.oodd.bank.dto;

import javax.persistence.Embeddable;

/**
 * 
 * @author caitlyn
 * Credit Card Object used in bank client and the shopping service 
 * to communicate credit card details between them. 
 * Shopping service makes sure the card is a valid VISA card with 3 CVV digits
 * then send it to bank then a bank would validate that card
 * NOTE: Only Visa validation is currently support
 */

@Embeddable
public class CreditCard {

    private String name="";

    private String endDate="";

    private String cardnumber="";

    private String cvv="111";

    private String issueNumber="01";

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return 
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 
     * @param endDate 
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 
     * @return 
     */
    public String getCardnumber() {
        return cardnumber;
    }

    /**
     * 
     * @param cardnumber 
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    /**
     * 
     * @return 
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * 
     * @param cvv 
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * 
     * @return 
     */
    public String getIssueNumber() {
        return issueNumber;
    }

    /**
     * 
     * @param issueNumber 
     */
    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "CreditCard{" + "name=" + name + ", endDate=" + endDate + ", cardnumber=" + cardnumber + ", cvv=NOT PRINTED" +  ", issueNumber=" + issueNumber + '}';
    }
}
