package com.bikleek.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.bikleek.dto.Cliente;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class ManejoPeticionApiExterna {

	private static final String URL_BACKEND = "http://localhost:8080/api/clientes";
	
	public static List<Cliente> peticionGet() throws IOException, InterruptedException {
		
		List<Cliente> listadoClientes = null;
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.header("accept","application/json")
				.uri(URI.create(URL_BACKEND))
				.build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		Gson gson = new Gson();
		Cliente[] clientesArray = gson.fromJson(response.body(), Cliente[].class);
	
		listadoClientes = new ArrayList<Cliente>(Arrays.asList(clientesArray));
		
		return listadoClientes;
		
	}
	
	public static Cliente peticionPost(Cliente clienteNuevo) throws IOException, InterruptedException {
		
		Gson gson = new Gson();
	
		String representacionClienteJsonFormateada = gson.toJson(clienteNuevo);

		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.header("Content-Type","application/json")
				.uri(URI.create(URL_BACKEND))
				.POST(HttpRequest.BodyPublishers.ofString(representacionClienteJsonFormateada))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		Cliente clienteCreado = gson.fromJson(response.body(), Cliente.class);
		
		return clienteCreado;
		
	}
	
	public static Cliente peticionPut(Cliente clienteModificado) throws IOException, InterruptedException {
		
		Gson gson = new Gson();
	
		String representacionClienteJsonFormateada = gson.toJson(clienteModificado);

		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.header("Content-Type","application/json")
				.uri(URI.create(URL_BACKEND))
				.PUT(HttpRequest.BodyPublishers.ofString(representacionClienteJsonFormateada))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		Cliente clienteActualizado = gson.fromJson(response.body(), Cliente.class);
		
		return clienteActualizado;
		
	}
	
	public static boolean peticionDelete(Cliente clienteAEliminar) throws IOException, InterruptedException {
		
		boolean respuesta = false;
		
		String urlConParametro = URL_BACKEND+"/"+clienteAEliminar.getId();
		
		System.out.println("rul con parametro"+urlConParametro);

		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.header("Content-Type","application/json")
				.uri(URI.create(urlConParametro))
				.DELETE()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode()==200) {
			respuesta = true;
		}
		
		return respuesta;
		
	}
	

}
