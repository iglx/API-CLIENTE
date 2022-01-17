package br.com.bancointer.corretora.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.bancointer.corretora.entity.Empresa;

public class EmpresaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String nome;
	private String ticker;
	private Double valor;
	private StatusEmpresaEnum status;

	public EmpresaDTO() {
	}

	public EmpresaDTO(Long id, String nome, String ticker, Double valor, StatusEmpresaEnum status) {
		super();
		this.id = id;
		this.nome = nome;
		this.ticker = ticker;
		this.valor = valor;
		this.status = status;
	}

	public EmpresaDTO(Empresa entity) {
		super();
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.ticker = entity.getTicker();
		this.valor = entity.getValor();
		this.status = entity.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public StatusEmpresaEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEmpresaEnum status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
