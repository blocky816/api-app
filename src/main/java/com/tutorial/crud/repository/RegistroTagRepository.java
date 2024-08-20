package com.tutorial.crud.repository;

import com.tutorial.crud.dto.LicensePlateDTO;
import com.tutorial.crud.dto.RegistroTagDTO;
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

	@Query(value = "SELECT new com.tutorial.crud.dto.RegistroTagDTO(r.id, r.idChip, r.parking.idVentaDetalle, r.activo, r.fechaFin, r.club) " +
			"FROM RegistroTag r " +
			"WHERE r.parking.idVentaDetalle IS NOT NULL " +
			"AND (r.club = :clubName OR r.club = 'CIMERA') AND r.fechaFin > (CURRENT_DATE - 365)")
	List<RegistroTagDTO> getChipsByClub(String clubName);

	@Query(value = "SELECT new com.tutorial.crud.dto.RegistroTagDTO(r.id, r.idChip, r.parking.idVentaDetalle, r.activo, r.fechaFin, r.club) " +
			"FROM RegistroTag r " +
			"WHERE r.parking.idVentaDetalle IS NOT NULL " +
			"and (club = :clubName or club='CIMERA' or club = 'Sports Plaza' or club = 'Club Alpha 3') and fecha_fin > (current_date - 365)")
	List<RegistroTagDTO> getChipsByClub2(String clubName);

	@Query(value = "SELECT new com.tutorial.crud.dto.RegistroTagDTO(r.id, r.idChip, r.parking.idVentaDetalle, r.activo, r.fechaFin, r.club) " +
			"FROM RegistroTag r " +
			"WHERE r.parking.idVentaDetalle IS NOT NULL " +
			"AND (r.club = :clubName OR r.club = 'CIMERA' OR r.club = 'Club Alpha 2') AND r.fechaFin > (CURRENT_DATE - 365)")
	List<RegistroTagDTO> getChipsByClub3(String clubName);

	@Query(value = "SELECT new com.tutorial.crud.dto.LicensePlateDTO(r.id, r.idChip, p.idVentaDetalle, r.activo, r.fechaFin, r.club, c.idCliente, ca.placa) " +
			"FROM RegistroTag r " +
			"JOIN r.parking p " +
			"JOIN p.cliente c " +
			"JOIN p.carro ca " +
			"WHERE p.idVentaDetalle IS NOT NULL " +
			"AND (r.club = :clubName OR r.club = 'CIMERA') AND r.fechaFin > (CURRENT_DATE - 365)")
	List<LicensePlateDTO> getLicensePlatesByClub(String clubName);

	@Query(value = "SELECT new com.tutorial.crud.dto.LicensePlateDTO(r.id, r.idChip, p.idVentaDetalle, r.activo, r.fechaFin, r.club, c.idCliente, ca.placa) " +
			"FROM RegistroTag r " +
			"JOIN r.parking p " +
			"JOIN p.cliente c " +
			"JOIN p.carro ca " +
			"WHERE p.idVentaDetalle IS NOT NULL " +
			"and (r.club = :clubName or r.club='CIMERA' or r.club = 'Sports Plaza' or r.club = 'Club Alpha 3') and fecha_fin > (current_date - 365)")
	List<LicensePlateDTO> getLicensePlatesByClub2(String clubName);

	@Query(value = "SELECT new com.tutorial.crud.dto.LicensePlateDTO(r.id, r.idChip, p.idVentaDetalle, r.activo, r.fechaFin, r.club, c.idCliente, ca.placa) " +
			"FROM RegistroTag r " +
			"JOIN r.parking p " +
			"JOIN p.cliente c " +
			"JOIN p.carro ca " +
			"WHERE p.idVentaDetalle IS NOT NULL " +
			"AND (r.club = :clubName OR r.club = 'CIMERA' OR r.club = 'Club Alpha 2') AND r.fechaFin > (CURRENT_DATE - 365)")
	List<LicensePlateDTO> getLicensePlatesByClub3(String clubName);

	@Query("SELECT r.parking.cliente.idCliente FROM RegistroTag r WHERE r.parking.idVentaDetalle IS NOT NULL and r.club = 'Sports Plaza'")
	List<Integer> getClientIds();

	@Query("SELECT r.id, r.idChip, r.parking.idVentaDetalle, r.activo, r.fechaFin, r.club, r.parking.cliente.idCliente " +
			"FROM RegistroTag r " +
			"WHERE r.parking.idVentaDetalle IS NOT NULL " +
			"AND r.club = :clubName")
	List<Object[]> getLicensePlateData(String clubName);
}

