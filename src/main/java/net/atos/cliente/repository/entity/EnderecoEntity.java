package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_CLIENTE_ENDERECO")
public class EnderecoEntity {
	
	@EmbeddedId
	private EnderecoPK id;
	
	@Column(name = "LOGRADOURO")
	@NotNull(message="logradouro não pode ser nulo")
	private String logradouro;
	
	@Column(name = "BAIRRO")
	@NotNull(message="bairro não pode ser nulo")
	private String bairro;
	
	@Column(name = "CIDADE")
	@NotNull(message="cidade não pode ser nulo")
	private String cidade;
	
	@Column(name = "ESTADO")
	@NotNull(message="estado não pode ser nulo")
	private String estado;
	
	@Column(name = "CEP")
	@NotNull(message="cep não pode ser nulo")
	private String cep;
	
	@Column(name = "COMPLEMENTO")
	@NotNull(message="complemento não pode ser nulo")
	private String complemento;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public EnderecoPK getId() {
		return id;
	}

	public void setId(EnderecoPK id) {
		this.id = id;
	}
}
