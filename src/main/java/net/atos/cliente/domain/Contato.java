package net.atos.cliente.domain;

import javax.validation.constraints.NotNull;

public class Contato {
	
	
	@NotNull(message="tipo de contato do cliente não pode ser nulo")
	private TipoContatoEnum tipoContato;
	
	@NotNull(message="número não pode ser nulo")
	private String numero;


	public TipoContatoEnum getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContatoEnum tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
