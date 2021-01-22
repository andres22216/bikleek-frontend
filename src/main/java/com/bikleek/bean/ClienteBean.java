package com.bikleek.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;

import com.bikleek.dto.Cliente;
import com.bikleek.util.ManejoPeticionApiExterna;

@ManagedBean(name="clienteBean")
@SessionScoped
public class ClienteBean {
	
	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	private String mensajeErrorOExito = "";
	private UIData tblListaDatos;
	Cliente cliente = new Cliente();
	
	private String nombresClienteNuevo = "";
	private String apellidosClienteNuevo = "";
	private String correoClienteNuevo = "";
	private String direccionClienteNuevo = "";
	
	public ClienteBean() throws IOException, InterruptedException {
		super();
		clientes = ManejoPeticionApiExterna.peticionGet();
	}
	
	public void crearCliente(ActionEvent event) throws IOException, InterruptedException {
		mensajeErrorOExito = "";
	    Cliente clienteNuevo = new Cliente();
	    Cliente clienteCreado = null;
	    
		clienteNuevo.setNombre(this.nombresClienteNuevo);
		clienteNuevo.setApellido(this.apellidosClienteNuevo);
		clienteNuevo.setCorreo(this.correoClienteNuevo);
		clienteNuevo.setDireccion(this.direccionClienteNuevo);
		clienteNuevo.setFechaCreacion(null);
		
		clienteCreado = ManejoPeticionApiExterna.peticionPost(clienteNuevo);
		
		if(clienteCreado != null) {
			mensajeErrorOExito = "Cliente creado exitosamente con el id "+clienteCreado.getId();
			clientes.add(clienteCreado);
			
			this.nombresClienteNuevo = "";
			this.apellidosClienteNuevo = "";
			this.correoClienteNuevo = "";
			this.direccionClienteNuevo = "";
			
		}else {
			mensajeErrorOExito = "No fue posible crear el cliente "+clienteNuevo.getNombre();
		}
			
	}
	
	public void habilitarEdicion(ActionEvent event) {
		mensajeErrorOExito = "";
		Cliente clienteActual = (Cliente) getTblListaDatos().getRowData();
		clienteActual.setEditable(true);
	}
	
	public void cancelarActualizacion(ActionEvent event) {
		mensajeErrorOExito = "";
		Cliente clienteActual = (Cliente) getTblListaDatos().getRowData();
		clienteActual.setEditable(false);
	}
	
	public void actualizarCliente(ActionEvent event) throws IOException, InterruptedException {
		mensajeErrorOExito = "";
		Cliente clienteActual = (Cliente) getTblListaDatos().getRowData();
		
		clienteActual.setFechaCreacion(null);
		
		Cliente clienteActualizado = null;
		
		clienteActualizado = ManejoPeticionApiExterna.peticionPut(clienteActual);
		
		if(clienteActualizado != null) {
			mensajeErrorOExito = "Cliente actualizado exitosamente con el id "+clienteActualizado.getId();
			clientes = ManejoPeticionApiExterna.peticionGet();
		}else {
			mensajeErrorOExito = "No fue posible actualizar el cliente "+clienteActual.getNombre();
		}
		
	}
	
	public void eliminarCliente(ActionEvent event) throws IOException, InterruptedException {
		mensajeErrorOExito = "";
		Cliente clienteActual = (Cliente) getTblListaDatos().getRowData();
		if (ManejoPeticionApiExterna.peticionDelete(clienteActual)) {
			mensajeErrorOExito = "Cliente eliminado con exito";
			clientes = ManejoPeticionApiExterna.peticionGet();
		}else {
			mensajeErrorOExito = "No fue posible eliminar el cliente";
		}
	}

	public String getMensajeErrorOExito() {
		return mensajeErrorOExito;
	}

	public void setMensajeErrorOExito(String mensajeErrorOExito) {
		this.mensajeErrorOExito = mensajeErrorOExito;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}  
	
	public UIData getTblListaDatos() {
		return tblListaDatos;
	}

	public void setTblListaDatos(UIData tblListaDatos) {
		this.tblListaDatos = tblListaDatos;
	}

	public String getNombresClienteNuevo() {
		return nombresClienteNuevo;
	}

	public void setNombresClienteNuevo(String nombresClienteNuevo) {
		this.nombresClienteNuevo = nombresClienteNuevo;
	}

	public String getApellidosClienteNuevo() {
		return apellidosClienteNuevo;
	}

	public void setApellidosClienteNuevo(String apellidosClienteNuevo) {
		this.apellidosClienteNuevo = apellidosClienteNuevo;
	}

	public String getCorreoClienteNuevo() {
		return correoClienteNuevo;
	}

	public void setCorreoClienteNuevo(String correoClienteNuevo) {
		this.correoClienteNuevo = correoClienteNuevo;
	}

	public String getDireccionClienteNuevo() {
		return direccionClienteNuevo;
	}

	public void setDireccionClienteNuevo(String direccionClienteNuevo) {
		this.direccionClienteNuevo = direccionClienteNuevo;
	}
	
}
