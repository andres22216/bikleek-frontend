package com.bikleek.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

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
		
		clientes = ManejoPeticionApiExterna.peticionGet();
//		if(clientes != null) {
//			for (Cliente clienteAux : clientes) {
//				System.out.println("Identificador: "+clienteAux.getId());
//				System.out.println("Nombre: "+clienteAux.getNombre());
//				System.out.println("Apellido: "+clienteAux.getApellido());
//				System.out.println("Correo: "+clienteAux.getCorreo());
//			}
//		}
		
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
			
//			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			
			
		}else {
			mensajeErrorOExito = "No fue posible crear el cliente "+clienteNuevo.getNombre();
		}
			
	}
	
	public void habilitarEdicion(ActionEvent event) {
		System.out.println("ESTOY EN LA EDICION");
		mensajeErrorOExito = "";
		Cliente clienteActual = (Cliente) getTblListaDatos().getRowData();
		clienteActual.setEditable(true);
		System.out.println("Identificador: "+clienteActual.getId());
		System.out.println("Nombre: "+clienteActual.getNombre());
		System.out.println("Apellido: "+clienteActual.getApellido());
		System.out.println("Correo: "+clienteActual.getCorreo());
		
//		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//		try {
//			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
//		} catch (IOException e) {
//			TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
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
