package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RegistroTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegistroTagRepository extends JpaRepository<RegistroTag, Integer> {

	RegistroTag findByIdChip(long idChip);
	RegistroTag findByParking(ParkingUsuario idParking);

	@Modifying
	@Query(value = "update registro_tag set activo=false where (id_parking in "
			+ "(select id_venta_detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
			+ "estado_cobranza='Baja' AND id_venta_detalle is not null ) or current_date > fecha_fin) and"
			+ " (club='Futbol City' or club='CIMERA' or club='Club Alpha 2' or club='Club Alpha 3' or club = 'Sports Plaza')", nativeQuery = true)
	int disableChips();

	@Query(value = "SELECT * FROM registro_tag  WHERE id_parking is not null " +
			"and (club = ?1 or club='CIMERA') and fecha_fin > (current_date - 720)", nativeQuery = true)
	List<RegistroTag> getChipsByClub(String clubName);

	@Query(value = "SELECT * FROM registro_tag  WHERE id_parking is not null " +
			"and (club = ?1 or club='CIMERA' or club = 'Sports Plaza') and fecha_fin > (current_date - 720)", nativeQuery = true)
	List<RegistroTag> getChipsByClub2(String clubName);
}

