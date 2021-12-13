package net.atos.cliente.service;

import net.atos.cliente.domain.Cliente;

public class ClienteInativoEvent {

	private Cliente cliente;
	
	public ClienteInativoEvent(Cliente pcliente) {
		this.cliente = pcliente;		
	}

	public Cliente getCliente() {
		return cliente;
	}
	
}
