package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.DirectDebitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectDebitHistoryRepository extends JpaRepository<DirectDebitHistory, Long> {
    DirectDebitHistory findTopByCustomerAndCancellationDateIsNullOrderByDirectDebitDateDesc(Cliente customer);
}
