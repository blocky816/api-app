/*
 * Esta clase EstatusCobranzaDAO.java crearemos los métodos que utilizará la clase EstatusCobranzaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.EstatusCobranza;

//Los metodos creado en el ...DAOImpl.java
public interface EstatusCobranzaDAO {
	public List<EstatusCobranza> findAll();

    public EstatusCobranza findById(int id);

    public void save(EstatusCobranza objeto);
}
