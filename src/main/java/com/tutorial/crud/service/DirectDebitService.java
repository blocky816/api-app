package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.DirectDebitHistory;
import com.tutorial.crud.enums.CardType;
import com.tutorial.crud.repository.DirectDebitHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DirectDebitService {
    @Autowired
    DirectDebitHistoryRepository directDebitHistoryRepository;

    @Autowired
    ClienteService clienteService;

    public void createDirectDebit(int customerID, String cardNumber, CardType cardType) {
        Cliente customer = clienteService.findById(customerID);
        DirectDebitHistory history = new DirectDebitHistory();
        history.setCustomer(customer);
        history.setDirectDebitDate(LocalDateTime.now());
        history.setCardNumber(cardNumber);
        history.setCardType(cardType);
        directDebitHistoryRepository.save(history);
    }

    public void cancelDirectDebit(int customerID, String performedBy, String cancellationReason) {
        Cliente customer = clienteService.findById(customerID);

        // Buscar el último registro de domiciliación
        DirectDebitHistory history = directDebitHistoryRepository.findTopByCustomerAndCancellationDateIsNullOrderByDirectDebitDateDesc(customer);

        if (customer.isDomiciliado() && history != null) {
            customer.setDomiciliado(false);
            clienteService.save(customer);
            history.setCancellationDate(LocalDateTime.now());
            history.setCancellationReason(cancellationReason);
            history.setPerformedBy(performedBy);
            directDebitHistoryRepository.save(history);
        } else {
            throw new RuntimeException("No se encontraron registros de domiciliacion para: " + customerID);
        }
    }
}
