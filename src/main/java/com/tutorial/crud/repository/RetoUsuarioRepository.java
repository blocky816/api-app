package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Reto;
import com.tutorial.crud.entity.RetoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetoUsuarioRepository extends JpaRepository<RetoUsuario, UUID> {

	Optional<List<RetoUsuario>> findByActivo(boolean activo);

	RetoUsuario findByRetoAndCliente(Reto reto, Cliente cliente);

	@Query(value = "SELECT \n" +
			"Cast(reto.id_reto as varchar) idReto, reto.nombre, reto.descripcion, Cast(reto.tipo_reto as varchar) tipoReto, reto.no_inscritos,\n" +
			"reto.cupo_maximo, reto.fecha_inicio, reto.fecha_fin,\n" +
			"case when reto.id_reto in (select ru.id_reto from\n" +
			"cliente c inner join reto on c.idclub = reto.club\n" +
			"inner join reto_usuario as ru on ru.id_reto = reto.id_reto \n" +
			"where c.idcliente = :idCliente and reto.activo = true and ru.idcliente = c.idcliente and ru.id_reto = reto.id_reto) then true \n" +
			"else false\n" +
			"end\n" +
			"as is_inscrito,\n" +
			"reto.dispositivo, reto.club, reto.banner, reto.icono, reto.max\n" +
			"FROM cliente inner join reto on cliente.idclub = reto.club\n" +
			"where idcliente = :idCliente and reto.activo = true",
			nativeQuery = true)
	List<Tuple> getRetoUsuarioDTO(Integer idCliente);

}
