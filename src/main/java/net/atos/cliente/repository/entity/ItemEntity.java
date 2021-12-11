package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_CLIENTE_ITEM")
public class ItemEntity {
	
	@EmbeddedId
	private ItemPK id;
	
	@Column(name = "TELEFONE")
	@NotNull(message = "O número de telefone deve ser cadastrado")
	private String telefone;

	@Column(name = "CELULAR")
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
	
	public ItemPK getId() {
		return id;
	}
	public void setId(ItemPK id) {
		this.id = id;
	}
	
}