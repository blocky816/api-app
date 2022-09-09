/*En este paquete tenemos nuestro clase HorarioOtroClubId.java.
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.io.Serializable;
import java.util.Date;

// clase HorarioOtroClubId 
public class HorarioOtroClubId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int TerminalId;
	private Date Desde;
	private Date Hasta;
	
	//Se genero los getters y setters
	public int getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(int terminalId) {
		TerminalId = terminalId;
	}
	public Date getDesde() {
		return Desde;
	}
	public void setDesde(Date desde) {
		Desde = desde;
	}
	public Date getHasta() {
		return Hasta;
	}
	public void setHasta(Date hasta) {
		Hasta = hasta;
	}
	
	//Es un método de clase Java Integer que devuelve el código hash
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Desde == null) ? 0 : Desde.hashCode());
		result = prime * result + ((Hasta == null) ? 0 : Hasta.hashCode());
		result = prime * result + TerminalId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HorarioOtroClubId other = (HorarioOtroClubId) obj;
		if (Desde == null) {
			if (other.Desde != null)
				return false;
		} else if (!Desde.equals(other.Desde))
			return false;
		if (Hasta == null) {
			if (other.Hasta != null)
				return false;
		} else if (!Hasta.equals(other.Hasta))
			return false;
		if (TerminalId != other.TerminalId)
			return false;
		return true;
	}
	//Método donde pasa los parametros de Horario
	public HorarioOtroClubId(int terminalId, Date desde, Date hasta) {
		TerminalId = terminalId;
		Desde = desde;
		Hasta = hasta;
	}
	public HorarioOtroClubId() {
	}
	@Override
	public String toString() {
		return "HorarioOtroClubId [TerminalId=" + TerminalId + ", Desde=" + Desde + ", Hasta=" + Hasta + "]";
	}
	
}
