package net.atos.api.cliente.repository.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ContatoPK implements Serializable{
	
	/**
	 * SERIAL UID
	 */
	private static final long serialVersionUID = 5240150106898767721L;

	@Column(name = "NR_CONTATO")
	@NotNull(message = "N° do Contato não pode ser nulo")
	private Integer numeroContato;
	
	@ManyToOne
	@JoinColumn(name="ID_CADASTRO")
	private PessoaEntity pessoa;

	public Integer getNumeroContato() {
		return numeroContato;
	}

	public void setNumeroContato(Integer numeroContato) {
		this.numeroContato = numeroContato;
	}

	public PessoaEntity getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaEntity pessoa) {
		this.pessoa = pessoa;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(pessoa, numeroContato);
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
		return Objects.equals(pessoa, other.pessoa) && Objects.equals(numeroContato, other.numeroContato);
	}

}
