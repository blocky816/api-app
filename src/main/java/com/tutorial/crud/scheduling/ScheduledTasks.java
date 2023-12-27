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

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ClienteServiceImpl clienteService;

    @Scheduled(cron = "0 0 23 * * *") // Cron expression for running every minute
    public void updateCollectionAlpha2() {
        clienteService.actualizarActivosxClub(6);
        log.info("Clientes Alpha 2 activos a las {}", dateFormat.format(new Date()));
        clienteService.actualizarEtapasCanceladosxClub(6);
        log.info("Clientes Alpha 2 bajas y etapas actualizados a las {}", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 0 1 * * *") // Cron expression for running every minute
    public void updateCollectionAlpha3() {
        clienteService.actualizarActivosxClub(7);
        log.info("Clientes Alpha 3 activos a las {}", dateFormat.format(new Date()));
        clienteService.actualizarEtapasCanceladosxClub(7);
        log.info("Clientes Alpha 3 bajas y etapas actualizados a las {}", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 0 3 * * *") // Cron expression for running every minute
    public void updateCollectionCIMERA() {
        clienteService.actualizarActivosxClub(8);
        log.info("Clientes CIMERA activos a las {}", dateFormat.format(new Date()));
        clienteService.actualizarEtapasCanceladosxClub(8);
        log.info("Clientes CIMERA bajas y etapas actualizados a las {}", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 0 5 * * *") // Cron expression for running every minute
    public void updateCollectionSPORTS() {
        clienteService.actualizarActivosxClub(9);
        log.info("Clientes Sports Plaza activos a las {}", dateFormat.format(new Date()));
        clienteService.actualizarEtapasCanceladosxClub(9);
        log.info("Clientes Sports Plaza bajas y etapas actualizados a las {}", dateFormat.format(new Date()));
    }
}
