package com.tutorial.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.configuracion;

@Repository
public interface configuracionRepository extends JpaRepository<configuracion, Integer>{
	 Optional<configuracion> findByServiceName(String serviceName);
	 boolean existsByServiceName(String serviceName);
}
