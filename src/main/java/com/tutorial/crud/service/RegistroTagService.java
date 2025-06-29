package com.tutorial.crud.service;

import com.tutorial.crud.dto.LicensePlateDTO;
import com.tutorial.crud.dto.RegistroTagDTO;
import com.tutorial.crud.entity.Caseta;
import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RegistroTag;
import com.tutorial.crud.repository.CasetaRepository;
import com.tutorial.crud.repository.RegistroTagRepository;

import com.tutorial.crud.security.jwt.JwtEntryPoint;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RegistroTagService {
    private final static Logger logger = LoggerFactory.getLogger(RegistroTagService.class);
    @Autowired
    RegistroTagRepository registroTagRepository;
    @Autowired
    CasetaRepository casetaRepository;

    public List<RegistroTag> list(){
        return registroTagRepository.findAll();
    }

    public Optional<RegistroTag> getOne(int id){
        return registroTagRepository.findById(id);
    }

    public RegistroTag  save(RegistroTag actividad){
    	return registroTagRepository.save(actividad);
    }

    public RegistroTag findByIdChip(long idChip) {
    	return registroTagRepository.findByIdChip(idChip);
    }
    public RegistroTag findByParking(ParkingUsuario idParking) {
    	return registroTagRepository.findByParking(idParking);
    }

    public void disableChips() {
        logger.info("Disabling chips...");
        int size = registroTagRepository.disableChips();
        logger.info("Chips disabled: " + size);
    }

    public List<RegistroTagDTO> getChipsByClub(int casetaID) {
        Caseta caseta = casetaRepository.getOne(casetaID);
        String nombre = caseta.getClub().getNombre();
        logger.info("Consultando chips de caseta: " + casetaID + " => " + nombre + " ...");
        List<RegistroTagDTO> registroTagList = new ArrayList<>();

        boolean excludeEmpleado = (casetaID == 3 || casetaID == 4);
        switch (casetaID) {
            case 1:
            case 2:
                nombre = "Club Alpha 3";
                registroTagList = registroTagRepository.getChipsByClub3(nombre);
                break;
            case 3: //CIMERA
            case 4: //CIMERA
                nombre = "Futbol City";
                registroTagList = registroTagRepository.getChipsByClub(nombre, excludeEmpleado);
                break;
            case 5: // Futbol City
            case 9: // Futbol City
                nombre = "Futbol City";
                registroTagList = registroTagRepository.getChipsByClub(nombre, false);
                break;
            case 6:
            case 7:
                nombre = "Club Alpha 2";
                registroTagList = registroTagRepository.getChipsByClub2(nombre);
                break;
            case 8:
                nombre = "Sports Plaza";
                registroTagList = registroTagRepository.getChipsByClub(nombre, false);
                break;
        }

        logger.info("Chips consultados => " + registroTagList.size());
        return registroTagList;
    }

    public List<LicensePlateDTO> getLicensePlatesByClub(int clubID) {
        List<LicensePlateDTO> registroTagList = new ArrayList<>();
        String nombre = "";
        switch (clubID) {
            case 6: // Club Alpha 2
                nombre = "Club Alpha 2";
                registroTagList = registroTagRepository.getLicensePlatesByClub2(nombre);
                break;
            case 8:
                nombre = "Futbol City";
                registroTagList = registroTagRepository.getLicensePlatesByClub(nombre);
                break;
            case 7:
                nombre = "Club Alpha 3";
                registroTagList = registroTagRepository.getLicensePlatesByClub3(nombre);
                break;
            case 9:
                nombre = "Sports Plaza";
                registroTagList = registroTagRepository.getLicensePlatesByClub(nombre);
                break;
        }
        logger.info("Consultando placas de club: " + clubID + " => " + nombre + " ...");
        logger.info("Placas consultadas => " + registroTagList.size());
        return registroTagList;
    }
}
