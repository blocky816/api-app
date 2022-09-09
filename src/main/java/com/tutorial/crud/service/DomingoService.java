/*Como en el paquete anterior tendremos una clase y una interface. 
 * El servicio será el que hará de interemediario entre el DAO y 
 * el controlador(La clase que gestionará las peticiones de la API que veremos más adelante).
 * La interfaz de service tendría esta estructura DomingoService.java:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.util.List;

import com.tutorial.crud.entity.Domingo;
//Interfaz en el que manda a llamar los métodos creados en ...ServiceImpl.java
public interface DomingoService {

	public List<Domingo> findAll();

    public Domingo findById(int clave);

    public void save(Domingo domingo);
}

