package com.tutorial.crud.dto;

import com.google.gson.Gson;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 *
 * @author stark
 */

public class ClientePesaje extends Thread{
	private String url = "http://192.168.20.102:1235";
	private Socket socket;
	public void initCliente() {
		 try {
	            IO.Options options = new IO.Options();
	            options.transports = new String[]{"websocket"};
	            // Number of failed retries
	            options.reconnectionAttempts = 10;
	            // Time interval for failed reconnection
	            options.reconnectionDelay = 1000;
	            // Connection timeout (ms)
	            options.timeout = 500;
	            socket = IO.socket(url, options);
	           
	            socket.on(Socket.EVENT_CONNECT, objects -> {
	                System. out. println ("client:"+ "successful connection");
	            });
	            socket.on (Socket.EVENT_CONNECT, objects-> System.out.println ("client:"+"in connection"));
	            socket.on (Socket.EVENT_CONNECT_ERROR, objects-> System.out.println ("client:"+"connection failure"));
	            socket.connect();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		
	}
	public void enviarPesaje(Object pesaje) {
		Gson gson = new Gson();
		String json=gson.toJson(pesaje);
		socket.emit("pesaje", json);
		System.out.println("Se envio el pesaje...");
	}
}