package com.tutorial.crud.repository;

import com.tutorial.crud.entity.QRCortesia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QRCortesiaRepository extends JpaRepository<QRCortesia, UUID> {

	QRCortesia findByIdRegistro(String idRegistro);
	List<QRCortesia> findByRedimido(boolean redimido);


}
