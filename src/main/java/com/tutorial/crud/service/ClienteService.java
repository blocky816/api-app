/*Como en el paquete anterior tendremos una clase y una interface. 
 * El servicio será el que hará de interemediario entre el DAO y 
 * el controlador(La clase que gestionará las peticiones de la API que veremos más adelante).
 * La interfaz de service tendría esta estructura ClienteService.java:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tutorial.crud.dto.PlatinumUsers;
import com.tutorial.crud.entity.*;
import org.hibernate.query.Query;

import com.tutorial.crud.dto.ClienteDTOO;
import org.json.JSONArray;


//Interfaz en el que manda a llamar los métodos creados en ...ServiceImpl.java
public interface ClienteService {

	public List<Cliente> findAll();

    public Cliente findById(int clave);
    
    public Cliente findByNoMembresia(long clave);

    public void save(Cliente cliente);

	public boolean findCitas(Date date, CASala obtenerSala, int cliente);	

	public boolean findCitas(UUID idApartado, int cliente);

	public CAApartados findApartados(Date date, CASala obtenerSala, int cliente);

	public List<ClienteDTOO> findClientesByIdClub(int clubId);

	public List<ClienteDTOO> asistenciaClientes(UUID id);

	public List<ClienteDTOO> asistenciaGimnasioClientes(UUID id);

	public List<Cliente> findAllByEstatusMembresia();

	public void activateCustomer(int customerID, int statusCobranza);

	public String getPasswordHash(String customerID);

	//public void sendNewPasswordHash(String userID);
	//public List<Cliente> findAllByClub(Club club);
	public String getSinEtapa(int club) throws Exception;

	public String getConEtapa(int club) throws Exception;

	public void actualizarActivosxClub(int club);

	public void actualizarEtapasCanceladosxClub(int club);

	public Cliente findByIdOdoo(int OdooId);

	public List<PlatinumUsers> getPlatinumUsersByClub(int club);
}
