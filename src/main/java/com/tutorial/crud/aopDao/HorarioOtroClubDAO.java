/*
 * Esta clase HorarioOtroClubDAO.java crearemos los métodos que utilizará la clase HorarioOtroClubDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.HorarioOtroClub;
import com.tutorial.crud.entity.HorarioOtroClubId;

//Los metodos creado en el ...DAOImpl.java
public interface HorarioOtroClubDAO {
	public List<HorarioOtroClub> findAll();

    public HorarioOtroClub findById(HorarioOtroClubId id);

    public void save(HorarioOtroClub objeto);
}
