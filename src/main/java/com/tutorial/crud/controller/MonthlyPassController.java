package com.tutorial.crud.controller;

/*import com.tutorial.crud.dto.MonthlyPassProjection;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.MonthlyPass;
import com.tutorial.crud.service.ClienteService;*/
import com.tutorial.crud.dto.MonthlyPassDTO;
import com.tutorial.crud.service.MonthlyPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passes")
public class MonthlyPassController {
    @Autowired
    private MonthlyPassService monthlyPassService;
    /*@Autowired
    private ClienteService clienteService;

    @PostMapping("/generate-monthly-passes/{clubID}")
    public ResponseEntity<Void> generateMonthlyPasses(@PathVariable int clubID) {
        monthlyPassService.monthlyPassesGenerator(clubID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByCustomer/{customerID}")
    public ResponseEntity<List<MonthlyPassProjection>> getByCustomer(@PathVariable int customerID) {
        List<MonthlyPassProjection> passes =  monthlyPassService.getCurrentByCustomer(customerID);
        Cliente customer = clienteService.findById(customerID);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passes);
    }*/

    @GetMapping("/getPassesByMembershipType/{membershipType}")
    public ResponseEntity<MonthlyPassDTO> getByCustomer(@PathVariable String membershipType) {
        MonthlyPassDTO monthlyPassDTO =  monthlyPassService.getPassesByMembershipType(membershipType);

        return ResponseEntity.ok(monthlyPassDTO);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getMonthlyPasses")
    public ResponseEntity<List<MonthlyPassDTO>> getMonthlyPasses() {
        List<MonthlyPassDTO> monthlyPassDTOs =  monthlyPassService.getMonthlyPasses();

        return ResponseEntity.ok(monthlyPassDTOs);
    }
}
