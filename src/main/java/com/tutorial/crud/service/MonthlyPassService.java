package com.tutorial.crud.service;

import com.tutorial.crud.dto.MonthlyClassDTO;
import com.tutorial.crud.dto.MonthlyPassDTO;
/*import com.tutorial.crud.dto.MonthlyPassProjection;
import com.tutorial.crud.dto.PlatinumUsers;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.MonthlyClass;
import com.tutorial.crud.entity.MonthlyPass;*/
import com.tutorial.crud.enums.Month;
/*import com.tutorial.crud.repository.MonthlyClassRepository;
import com.tutorial.crud.repository.MonthlyPassRepository;
import com.tutorial.crud.scheduling.ScheduledTasks;*/
import com.tutorial.crud.repository.MonthlyClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlyPassService {
    private static final Logger log = LoggerFactory.getLogger(MonthlyPassService.class);
    /*@Autowired
    private MonthlyClassRepository monthlyClassRepository;
    @Autowired
    private MonthlyPassRepository monthlyPassRepository;
    @Autowired
    private ClienteService clienteService;*/
    @Autowired
    private MonthlyClassService monthlyClassService;

    @Autowired
    private MonthlyClassRepository monthlyClassRepository;

    /*@Transactional
    public void monthlyPassesGenerator(int club) {
        // Obtener el mes y año actual
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        // GET Monthly Classes
        List<MonthlyClass> monthlyClasses = monthlyClassRepository.findAllByMonthAndYearAndActiveTrue(month, year);
        log.info("Monthly classes list: {} Month: {} Year: {}", monthlyClasses.size(), month, year);

        // GET Platinum Users
        List<PlatinumUsers> platinumUsers = clienteService.getPlatinumUsersByClub(club);
        log.info("Platinum users: {}", platinumUsers.size());

        for (MonthlyClass monthlyClass : monthlyClasses) {
            for (PlatinumUsers platinumUser : platinumUsers) {
                Cliente customer = clienteService.findByIdCliente(platinumUser.getId());
                MonthlyPass monthlyPass = new MonthlyPass();
                switch (monthlyClass.getMembership()) {
                    case "INDIVIDUAL":
                        if(platinumUser.getTipo_membresia().equals("individual")) {
                            monthlyPass.setCustomer(customer);
                            monthlyPass.setMonthlyClass(monthlyClass);
                            monthlyPass.setQuantityAvailable(monthlyClass.getQuantity());
                        } else continue;
                    break;
                    case "DUAL":
                        if(platinumUser.getTipo_membresia().equals("dual")) {
                            monthlyPass.setCustomer(customer);
                            monthlyPass.setMonthlyClass(monthlyClass);
                            monthlyPass.setQuantityAvailable(monthlyClass.getQuantity());
                        } else continue;
                    break;
                    case "FAMILIAR":
                        if(platinumUser.getTipo_membresia().equals("grupal") || platinumUser.getTipo_membresia().equals("familiar")) {
                            monthlyPass.setCustomer(customer);
                            monthlyPass.setMonthlyClass(monthlyClass);
                            monthlyPass.setQuantityAvailable(monthlyClass.getQuantity());
                        } else continue;
                    break;
                }
                monthlyPassRepository.save(monthlyPass);
            }
        }
    }*/

    /*public List<MonthlyPassProjection> getCurrentByCustomer(int customerID) {
        Cliente customer = clienteService.findByIdCliente(customerID);
        return monthlyPassRepository.getByCustomer(customer);
    }*/

    public MonthlyPassDTO getPassesByMembershipType(String membershipType) {
        MonthlyPassDTO monthlyPassDTO = new MonthlyPassDTO();

        // Obtener el mes y año actual
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        String monthName = Month.getMonthName(month);

        String name = "Paquete " + membershipType + " " + monthName + " " + year;
        monthlyPassDTO.setNombre(name);

        List<MonthlyClassDTO> monthlyClassDTOS = monthlyClassService.getCurrentClasses(membershipType);
        // Construir el string final descritpion usando Streams
        String description = monthlyClassDTOS.stream()
                .map(classDTO -> classDTO.getUso_maximo() + " " + classDTO.getNombre())
                .collect(Collectors.joining(" - "));
        monthlyPassDTO.setDescripcion(description);

        monthlyPassDTO.setUso_maximo(1);
        monthlyPassDTO.setId(69);

        String code = "FCAPPAQ-" + membershipType + monthName + year;
        monthlyPassDTO.setCodigo(code);

        // Obtén el primer día del mes a las 00:00:00
        LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).atStartOfDay();

        // Obtén el último día del mes a las 23:59:59
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        LocalDateTime lastDayOfMonthEnd = lastDayOfMonth.atTime(23, 59, 59);
        monthlyPassDTO.setInicio(firstDayOfMonth);
        monthlyPassDTO.setFin(lastDayOfMonthEnd);
        monthlyPassDTO.setTipos_membresia(List.of(membershipType));
        monthlyPassDTO.setPases_incluidos(monthlyClassDTOS);
        return monthlyPassDTO;
    }

    public List<MonthlyPassDTO> getMonthlyPasses() {

        // Obtener el mes y año actual
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        List<String> membershipTypes = monthlyClassRepository.findDistinctActiveMembershipsByMonthAndYear(month, year);
        log.info("Memberships: {} Month: {} Year: {}", membershipTypes, month, year);

        List<MonthlyPassDTO> monthlyPassDTOS = new ArrayList<>();
        for(String membershipType: membershipTypes) {
            MonthlyPassDTO monthlyPassDTO = getPassesByMembershipType(membershipType.trim());
            monthlyPassDTOS.add(monthlyPassDTO);
        }
        return monthlyPassDTOS;
    }


}
