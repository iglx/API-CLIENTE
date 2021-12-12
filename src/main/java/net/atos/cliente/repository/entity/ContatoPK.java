package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ContatoPK {

	@Column(name = "NR_CONTATO")
	@NotNull(message = "N° do Contato não pode ser nulo")
	private Integer numeroContato;
	
	@ManyToOne
	private ClienteEntity cliente;

	public Integer getNumeroContato() {
		return numeroContato;
	}

	public void setNumeroContato(Integer numeroContato) {
		this.numeroContato = numeroContato;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

}
