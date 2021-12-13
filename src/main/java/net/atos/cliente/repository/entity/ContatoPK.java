package net.atos.cliente.repository.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ContatoPK {
	
	/**
	 * SERIAL UID
	 */
	private static final long serialVersionUID = 5240150106898767721L;

	@Column(name = "NR_CONTATO")
	@NotNull(message = "N° do Contato não pode ser nulo")
	private Integer numeroContato;
	
	@ManyToOne
	private PessoaEntity cliente;

	public Integer getNumeroContato() {
		return numeroContato;
	}

	public void setNumeroContato(Integer numeroContato) {
		this.numeroContato = numeroContato;
	}

	public PessoaEntity getCliente() {
		return cliente;
	}

	public void setCliente(PessoaEntity cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cliente, numeroContato);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContatoPK other = (ContatoPK) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(numeroContato, other.numeroContato);
	}

}
