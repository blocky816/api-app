package com.tutorial.crud.service;

import com.tutorial.crud.dto.EjercicioNuevoDTO;
import com.tutorial.crud.dto.RutinaNuevoDTO;
import com.tutorial.crud.entity.EjercicioNuevo;
import com.tutorial.crud.entity.RutinaEjercicioNuevo;
import com.tutorial.crud.entity.RutinaNuevo;
import com.tutorial.crud.repository.RutinaNuevoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RutinaNuevoService {

    @Autowired
    RutinaNuevoRepository rutinaNuevoRepository;
    @Autowired
    RutinaEjercicioNuevoService rutinaEjercicioNuevoService;
    @Autowired
    EjercicioNuevoService ejercicioNuevoService;

    //metodo save, getOne y delete
    public void save(RutinaNuevo rutinaNuevo){
        rutinaNuevoRepository.save(rutinaNuevo);
    }

    public List<RutinaNuevo> findAllByActivo(Boolean activo) {
        return rutinaNuevoRepository.findAllByActivo(activo);
    }

    public Optional<RutinaNuevo> findById(int id){
        return rutinaNuevoRepository.findById(id);
    }

    public List<RutinaNuevo> findAllByActivoAndTipoPlantilla() {
        return rutinaNuevoRepository.findAllByActivoAndTipoPlantilla();
    }

    public List<RutinaNuevoDTO> findBySegmentoAndTipoPlantillaAndActivo(String segmento, String tipoPlantilla, Boolean activo) {
        List<RutinaNuevo> workoutPlans = rutinaNuevoRepository.findBySegmentoAndTipoPlantillaAndActivo(segmento, tipoPlantilla, activo);
        List<RutinaNuevoDTO> rutinasGenerales = new ArrayList<>();

        if (!workoutPlans.isEmpty()){
            for (RutinaNuevo rutinaIterator: workoutPlans) {
                RutinaNuevoDTO rutina = new RutinaNuevoDTO();

                rutina.setId(rutinaIterator.getId());
                rutina.setNombreRutina(rutinaIterator.getNombreRutina());
                rutina.setNombreObjetivo(rutinaIterator.getNombreObjetivo());
                rutina.setSemanas(rutinaIterator.getSemanas());
                rutina.setTipoPlantilla(rutinaIterator.getTipoPlantilla());
                rutina.setSegmento(rutinaIterator.getSegmento());

                List<RutinaEjercicioNuevo> listaEjercicios = rutinaEjercicioNuevoService.findAllByRutinanuevo(rutinaIterator);
                List<EjercicioNuevoDTO> ejercicioNuevos = new ArrayList<>();
                for (RutinaEjercicioNuevo ejercicios: listaEjercicios){
                    Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(ejercicios.getEjercicio().getId());
                    EjercicioNuevoDTO nuevoDTO = new EjercicioNuevoDTO();
                    nuevoDTO.setId(ejercicio.get().getId());
                    nuevoDTO.setNombre(ejercicio.get().getNombre());
                    nuevoDTO.setDia(ejercicios.getDia());
                    nuevoDTO.setClub(ejercicio.get().getClub());
                    nuevoDTO.setNivel(ejercicio.get().getNivel());
                    nuevoDTO.setTipo(ejercicio.get().getTipo());
                    nuevoDTO.setImagen(ejercicio.get().getImagen());
                    //ejercicio.get().setDia(ejercicios.getDia());
                    ejercicioNuevos.add(nuevoDTO);
                }
                rutina.setEjercicios(ejercicioNuevos);
                rutinasGenerales.add(rutina);
            }
        }
        return rutinasGenerales;
    }
}
