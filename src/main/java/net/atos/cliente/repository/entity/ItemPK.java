package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ItemPK {

	@Column(name = "NR_ITEM")
	@NotNull(message = "N° do Item não pode ser nulo")
	private Integer numeroItem;
	
	@ManyToOne
	private ClienteEntity cliente;

	public Integer getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(Integer numeroItem) {
		this.numeroItem = numeroItem;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

}
