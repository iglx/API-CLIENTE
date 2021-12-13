package net.atos.cliente.service;

import net.atos.cliente.domain.PessoaVO;

public class ClienteInativoEvent {

	private PessoaVO cliente;
	
	public ClienteInativoEvent(PessoaVO pcliente) {
		this.cliente = pcliente;		
	}

	public PessoaVO getCliente() {
		return cliente;
	}
	
}
