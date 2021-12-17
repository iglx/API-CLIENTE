package net.atos.servicos.domain;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PessoaVO {
	
	private Long id;
	private String email;
	private String tipoPessoaEnum;
	private LocalDate dataEmissao;
	private String nrCpf;
	private String nrCnpj;
	private BigDecimal valor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoPessoaEnum() {
		return tipoPessoaEnum;
	}
	public void setTipoPessoaEnum(String tipoPessoaEnum) {
		this.tipoPessoaEnum = tipoPessoaEnum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getNrCpf() {
		return nrCpf;
	}
	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}
	public String getNrCnpj() {
		return nrCnpj;
	}
	public void setNrCnpj(String nrCnpj) {
		this.nrCnpj = nrCnpj;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
