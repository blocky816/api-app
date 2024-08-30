package com.tutorial.crud.dto;

import com.tutorial.crud.enums.CardType;

public class DirectDebitRequestDTO {
    private int customerID;
    private String cardNumber;
    private CardType cardType;
    private String performedBy;
    private String reason;

    public DirectDebitRequestDTO() {}

    public DirectDebitRequestDTO(int customerID, String cardNumber, String cardType) {
        this.customerID = customerID;
        this.cardNumber = cardNumber;
        this.cardType = CardType.fromString(cardType); // Usa el método estático del enum aquí
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DirectDebitRequestDTO{" +
                "customerID=" + customerID +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType=" + cardType +
                ", performedBy='" + performedBy + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
