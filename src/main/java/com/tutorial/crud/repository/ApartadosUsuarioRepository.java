package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

@Repository
public interface ApartadosUsuarioRepository extends JpaRepository<CAApartadosUsuario, UUID> {
    @Query(value = "select 1\n" +
            "from ca_apartados ca join ca_apartados_usuario cau on ca.id_apartados = cau.id_apartados\n" +
            "left outer join registro_gimnasio rg on ca.id_apartados = rg.id_apartados\n" +
            "where rg.id is null and ca.activo = true and cau.activo = true\n" +
            "and cau.created > (CURRENT_TIMESTAMP - INTERVAL '15 DAYS' - INTERVAL '5 HOURS')\n" +
            "and cau.idcliente = :customerId\n" +
            "and cau.idcliente = rg.id_cliente\n" +
            "and id_horario = :scheduleId\n" +
            "group by id_horario,cau.idcliente having count(*) > 3",
            nativeQuery = true)
    List<Tuple> isCustomerPenalized(int customerId, UUID scheduleId);
}
