package br.com.bancointer.corretora.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.bancointer.corretora.entity.Ordem;

public class OrdemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	private String cpf;

	private Double valorInvestimento;

	private Long numeroEmpresa;

	@JsonProperty(access = Access.READ_ONLY)
	private Double troco;

	public OrdemDTO() {
	}

	public OrdemDTO(Long id, String cpf, Double valorInvestimento, Long numeroEmpresa, Double troco) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.valorInvestimento = valorInvestimento;
		this.numeroEmpresa = numeroEmpresa;
		this.troco = troco;
	}

	public OrdemDTO(Ordem entity) {
		id = entity.getId();
		cpf = entity.getCpf();
		valorInvestimento = entity.getValorInvestimento();
		numeroEmpresa = entity.getNumeroEmpresa();
		troco = entity.getTroco();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getValorInvestimento() {
		return valorInvestimento;
	}

	public void setValorInvestimento(Double valorInvestimento) {
		this.valorInvestimento = valorInvestimento;
	}

	public Long getNumeroEmpresa() {
		return numeroEmpresa;
	}

	public void setNumeroEmpresa(Long numeroEmpresa) {
		this.numeroEmpresa = numeroEmpresa;
	}

	public Double getTroco() {
		return troco;
	}

	public void setTroco(Double troco) {
		this.troco = troco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
