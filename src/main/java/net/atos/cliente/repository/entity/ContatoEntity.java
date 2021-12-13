package net.atos.cliente.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.atos.cliente.domain.TipoContatoEnum;

@Entity
@Table(name = "TB_CLIENTE_CONTATO")
public class ContatoEntity implements Serializable {
	
	/**
	 * SERIAL UID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContatoPK id;
	
	@Column(name = "TPO_CONTATO")
	@NotNull(message = "Campo TipoContato do contato não pode ser nulo")
	private TipoContatoEnum tipoContato;
	
	@Column(name = "NUMERO")
	@NotNull(message = "número do contato não pode ser nulo")
	private String numero;

	public ContatoPK getId() {
		return id;
	}

	public void setId(ContatoPK id) {
		this.id = id;
	}

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