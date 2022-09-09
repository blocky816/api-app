package com.tutorial.crud.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ArrayPrueba {
	
	ArrayList<ArrayPrueba2> body;

	public ArrayList<ArrayPrueba2> getBody() {
		return body;
	}

	public void setBody(ArrayList<ArrayPrueba2> body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ArrayPrueba [body=" + body + "]";
	}
	
	


	
}
