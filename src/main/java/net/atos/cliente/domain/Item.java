package net.atos.cliente.domain;

import javax.validation.constraints.NotNull;

public class Item {
	
	@NotNull(message = "O número de telefone deve ser cadastrado")
	private String telefone;
	@NotNull(message = "O celular deve ser cadastrado")
	private String celular;
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}

}
