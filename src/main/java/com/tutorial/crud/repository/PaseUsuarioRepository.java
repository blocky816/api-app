package com.tutorial.crud.repository;

import com.tutorial.crud.Odoo.Spec.dto.PaseHealthStudioDTO;
import com.tutorial.crud.Odoo.Spec.dto.PaseReporteDTO;
import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
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

	List<PaseUsuario> findByClienteAndIdProdInAndActivoTrue(Cliente cliente, List<Integer> idProductos);

	List<PaseUsuario> findByClienteAndIdVentaDetalleAndActivoTrue(Cliente cliente, Integer idProducto);

	@Query(value = "SELECT * FROM pase_usuario p WHERE p.idcliente = :clienteId AND p.activo = TRUE AND " +
			"LOWER(p.concepto) LIKE ANY (:keywords)", nativeQuery = true)
	List<PaseUsuario> findByClienteAndConceptoContainingKeywordsNative(
			@Param("clienteId") Integer clienteId,
			@Param("keywords") String[] keywords);

	@Query(value = "SELECT * FROM pase_usuario p WHERE p.idcliente = :clienteId AND p.activo = TRUE AND " +
			"(LOWER(p.concepto) LIKE :keyword1 OR LOWER(p.concepto) LIKE :keyword2 OR " +
			"LOWER(p.concepto) LIKE :keyword3 OR LOWER(p.concepto) LIKE :keyword4 OR " +
			"LOWER(p.concepto) LIKE :keyword5 OR LOWER(p.concepto) LIKE :keyword6) " +
			"ORDER BY p.concepto",
			nativeQuery = true)
	List<PaseUsuario> findByClienteAndConceptoUsingDynamicKeywords(
			@Param("clienteId") Integer clienteId,
			@Param("keyword1") String keyword1,
			@Param("keyword2") String keyword2,
			@Param("keyword3") String keyword3,
			@Param("keyword4") String keyword4,
			@Param("keyword5") String keyword5,
			@Param("keyword6") String keyword6);

}
