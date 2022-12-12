package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RHEmpleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface RHEmpleadoRepository extends JpaRepository<RHEmpleado, Integer> {
	RHEmpleado findByIdEmpleado(int idEMpleado);

	@Query(value = "select id_empleado, empleado, club, fecha_nacimiento from rh_empleado " + 
	"where (cast(fecha_nacimiento as varchar) like concat('%-',:mes,'-',:dia,'%')) and activo = 'SI'",
			nativeQuery = true)
	List<Tuple> getEmpleadosCumpleaños(String dia, String mes);
}
