package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAHorario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HorarioRepository extends JpaRepository<CAHorario, UUID> {

	Optional<List<CAHorario>> findByActivo(boolean activo);
 

}
