package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PaseUsuarioRepository extends JpaRepository<PaseUsuario, Integer> {

	Optional<List<PaseUsuario>> findByActivo(boolean activo);

	PaseUsuario findByIdVentaDetalle(int idVentaDetalle);

	List<PaseUsuario> findByClienteAndIdProdAndActivoTrueOrderByCreated(Cliente cliente, int idProd);

	List<PaseUsuario> findByClienteAndActivoFalseAndPagadoIsNullAndFechaPagoIsNull(Cliente cliente);

	List<PaseUsuario> findByClienteAndActivoTrueAndPagadoIsNotNullAndFechaPagoIsNotNull(Cliente cliente);

	@Query("SELECT p FROM PaseUsuario p WHERE p.cliente = :cliente AND p.idProd in (:idProductos) AND "
			+ "FUNCTION('MONTH', p.fechaPago) = FUNCTION('MONTH', CURRENT_TIMESTAMP) AND "
			+ "FUNCTION('YEAR', p.fechaPago) = FUNCTION('YEAR', CURRENT_TIMESTAMP) "
			+ "ORDER BY p.fechaPago")
	List<PaseUsuario> findByClienteAndUltimoUsoIsNotNullAndFechaPagoMesActual(@Param("cliente") Cliente cliente, @Param("idProductos") Set<Integer> idProductos);
}
