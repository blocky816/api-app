package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAApartados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartadosRepository extends JpaRepository<CAApartados, UUID> {

	Optional<List<CAApartados>> findByActivo(boolean activo);

	@Query(value = "select * from ca_apartados ca where ca.activo = true and ca.id_horario = :idHorario and " +
			"TO_DATE(ca.dia, 'YYYY-MM-DD') >= CURRENT_DATE order by ca.dia",
			nativeQuery = true)
	List<CAApartados> apartadosMasivos(UUID idHorario);
}
