package net.atos.cliente.repository.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class EnderecoPK {

	/**
	 * SERIAL UID
	 */
	private static final long serialVersionUID = 5240150106898767721L;

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

	@Override
	public int hashCode() {
		return Objects.hash(cliente, numeroEndereco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoPK other = (EnderecoPK) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(numeroEndereco, other.numeroEndereco);
	}

}
