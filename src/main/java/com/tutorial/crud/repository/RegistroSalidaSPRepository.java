package com.tutorial.crud.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.entity.RegistroSalidaSP;
import com.tutorial.crud.entity.TipoAcceso;


@Repository
public interface RegistroSalidaSPRepository extends JpaRepository<RegistroSalidaSP, Integer> {
	
	List<RegistroSalidaSP> findByAcceso(boolean acceso);
	
	List<RegistroSalidaSP> findByTipoAcceso(TipoAcceso tipoAcceso);

	List<RegistroSalidaSP> findByFolio(int folio);

}
