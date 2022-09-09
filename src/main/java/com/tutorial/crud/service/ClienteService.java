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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.query.Query;

import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.Cliente;


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
}
