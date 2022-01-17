package br.com.bancointer.corretora.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.bancointer.corretora.entity.OrdemEmpresa;

public class OrdemEmpresaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	private Long quantidade;

	private Double valorTotal;

	private EmpresaDTO empresa;

	private OrdemDTO ordem;

	public OrdemEmpresaDTO() {
	}

	public OrdemEmpresaDTO(Long id, Long quantidade, Double valorTotal, EmpresaDTO empresa, OrdemDTO ordem) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.empresa = empresa;
		this.ordem = ordem;
	}

	public OrdemEmpresaDTO(OrdemEmpresa entity) {
		id = entity.getId();
		quantidade = entity.getQuantidade();
		valorTotal = entity.getValorTotal();
		empresa = new EmpresaDTO(entity.getEmpresa());
		ordem = new OrdemDTO(entity.getOrdem());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}

	public OrdemDTO getOrdem() {
		return ordem;
	}

	public void setOrdem(OrdemDTO ordem) {
		this.ordem = ordem;
	}

}
