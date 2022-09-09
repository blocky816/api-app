package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RegistroTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroTagRepository extends JpaRepository<RegistroTag, Integer> {

	RegistroTag findByIdChip(long idChip);
	RegistroTag findByParking(ParkingUsuario idParking);

}
