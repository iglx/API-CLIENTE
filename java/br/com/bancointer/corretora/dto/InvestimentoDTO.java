package br.com.bancointer.corretora.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InvestimentoDTO {

	@JsonIgnore
	private Long idOrdemEmpresa;

	@JsonIgnore
	private Long idEmpresa;

	private String nomeEmpresa;

	private Long quantidade;

	private Double valorTotal;

	private Double troco;

	InvestimentoDTO() {

	}

	public InvestimentoDTO(Long idOrdemEmpresa, Long idEmpresa, String nomeEmpresa, Long quantidade, Double valorTotal,
			Double troco) {
		super();
		this.idOrdemEmpresa = idOrdemEmpresa;
		this.idEmpresa = idEmpresa;
		this.nomeEmpresa = nomeEmpresa;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.troco = troco;
	}

	public Long getIdOrdemEmpresa() {
		return idOrdemEmpresa;
	}

	public void setIdOrdemEmpresa(Long idOrdemEmpresa) {
		this.idOrdemEmpresa = idOrdemEmpresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getTroco() {
		return troco;
	}

	public void setTroco(Double troco) {
		this.troco = troco;
	}
}
