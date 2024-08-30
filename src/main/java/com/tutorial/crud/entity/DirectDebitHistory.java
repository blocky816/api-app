package com.tutorial.crud.entity;

import com.tutorial.crud.enums.CardType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "direct_debit_history")
public class DirectDebitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Cliente customer;

    @Column(name = "direct_debit_date", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime directDebitDate;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private CardType cardType; // Puede ser "MasterCard" o "Visa"

    @Column(name = "cancellation_date", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime cancellationDate;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCustomer() {
        return customer;
    }

    public void setCustomer(Cliente customer) {
        this.customer = customer;
    }

    public LocalDateTime getDirectDebitDate() {
        return directDebitDate;
    }

    public void setDirectDebitDate(LocalDateTime directDebitDate) {
        this.directDebitDate = directDebitDate;
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

    public LocalDateTime getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(LocalDateTime cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    @Override
    public String toString() {
        return "DirectDebitHistory{" +
                "id=" + id +
                ", customer=" + customer +
                ", directDebitDate=" + directDebitDate +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cancellationDate=" + cancellationDate +
                ", performedBy='" + performedBy + '\'' +
                ", cancellationReason='" + cancellationReason + '\'' +
                '}';
    }
}
