package com.tutorial.crud.repository;

import com.tutorial.crud.dto.MonthlyPassProjection;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.MonthlyPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyPassRepository extends JpaRepository<MonthlyPass, Long> {
//    List<MonthlyPass> findAllByCustomerAndPassActiveTrue(Cliente customer);
//@Query("SELECT new com.tutorial.crud.dto.MonthlyPassProjection(mp.monthlyClass.className, mp.passActive, mp.quantityAvailable) " +
        @Query("SELECT mp.monthlyClass.className AS className, mp.passActive AS passActive, mp.quantityAvailable AS quantityAvailable " +
            "FROM MonthlyPass mp " +
            "WHERE mp.customer = :customer AND mp.monthlyClass.active = true")
    List<MonthlyPassProjection> getByCustomer(@Param("customer") Cliente customer);
}
