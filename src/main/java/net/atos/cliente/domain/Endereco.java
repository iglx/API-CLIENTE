package net.atos.cliente.domain;

import javax.validation.constraints.NotNull;

import net.atos.cliente.repository.entity.EnderecoPK;

public class Endereco {
	
private EnderecoPK id;

	@NotNull(message="logradouro não pode ser nulo")
	private String logradouro;
	@NotNull(message="bairro não pode ser nulo")
	private String bairro;
	@NotNull(message="cidade não pode ser nulo")
	private String cidade;
	@NotNull(message="estado não pode ser nulo")
	private String estado;
	@NotNull(message="cep não pode ser nulo")
	private String cep;
	@NotNull(message="complemento não pode ser nulo")
	private String complemento;
	
	public EnderecoPK getId() {
		return id;
	}

	public void setId(EnderecoPK id) {
		this.id = id;
	}
	
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
}
