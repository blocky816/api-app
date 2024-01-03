package com.tutorial.crud.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tutorial.crud.service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private ClienteServiceImpl clienteService;

    @Scheduled(cron = "0 0 0 * * *") // Cron expression for running every minute
    public void updateCollectionAlpha2() {
        clienteService.actualizarActivosxClub(6);
        clienteService.actualizarEtapasCanceladosxClub(6);
    }

    @Scheduled(cron = "0 30 2 * * *") // Cron expression for running every minute
    public void updateCollectionAlpha3() {
        clienteService.actualizarActivosxClub(7);
        clienteService.actualizarEtapasCanceladosxClub(7);
    }

    @Scheduled(cron = "0 0 4 * * *") // Cron expression for running every minute
    public void updateCollectionCIMERA() {
        clienteService.actualizarActivosxClub(8);
        clienteService.actualizarEtapasCanceladosxClub(8);
    }

    @Scheduled(cron = "0 0 6 * * *") // Cron expression for running every minute
    public void updateCollectionSPORTS() {
        clienteService.actualizarActivosxClub(9);
        clienteService.actualizarEtapasCanceladosxClub(9);
    }
}
