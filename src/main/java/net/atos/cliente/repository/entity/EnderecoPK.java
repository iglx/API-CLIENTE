package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class EnderecoPK {

	@Column(name = "ENDERECO")
	@NotNull(message = "Endereço não pode ser nulo")
	private Integer numeroEndereco;
	
	@ManyToOne
	private ClienteEntity cliente;

	public Integer getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(Integer numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

}
