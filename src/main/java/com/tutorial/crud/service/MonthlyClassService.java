package com.tutorial.crud.service;

import com.tutorial.crud.dto.MonthlyClassDTO;
import com.tutorial.crud.dto.MonthlyPassDTO;
import com.tutorial.crud.entity.MonthlyClass;
import com.tutorial.crud.repository.MonthlyClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyClassService {
    private static final Logger log = LoggerFactory.getLogger(MonthlyClassService.class);
    @Autowired
    MonthlyClassRepository monthlyClassRepository;

    public List<MonthlyClassDTO> getCurrentClasses(String membershipType) {
        // Obtener el mes y año actual
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        // GET Monthly Classes
        List<MonthlyClass> monthlyClasses = monthlyClassRepository.findAllByMonthAndYearAndMembershipAndActiveTrue(month, year, membershipType);
        log.info("Monthly classes list: {} Month: {} Year: {}", monthlyClasses.size(), month, year);

        List<MonthlyClassDTO> monthlyClassDTOS = new ArrayList<>();
        for (MonthlyClass monthlyClass: monthlyClasses) {
            MonthlyClassDTO monthlyClassDTO = new MonthlyClassDTO();
            monthlyClassDTO.setId(monthlyClass.getId());
            monthlyClassDTO.setNombre(monthlyClass.getClassName());
            monthlyClassDTO.setUso_maximo(monthlyClass.getQuantity());
            monthlyClassDTOS.add(monthlyClassDTO);
        }
        return monthlyClassDTOS;
    }
}


