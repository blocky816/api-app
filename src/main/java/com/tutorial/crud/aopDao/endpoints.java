package com.tutorial.crud.aopDao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tutorial.crud.entity.Movimientos;



public class endpoints {
	
	public String conectaApiClubPOST(String body, String endpoint) {
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(endpoint)).header("Content-Type", "application/json").POST(BodyPublishers.ofString(body)).build();
		return conexion(request1);
	}
	
	public String conectaApiClubGET(String body) {
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(body)).header("Content-Type", "application/json").GET().build();
		return conexion(request1);
	}
	
	private String conexion(HttpRequest request1) {
	   	CompletableFuture<String> client = HttpClient.newHttpClient().sendAsync(request1, BodyHandlers.ofString()).thenApply(HttpResponse::body);
    	String json = "";
	   	try {
			json = String.valueOf(client.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	return json;
	}
}
