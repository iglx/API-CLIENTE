package br.com.bancointer.corretora.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "tb_ordem")
public class Ordem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_ord")
	@SequenceGenerator(name = "sq_ord", sequenceName = "sequence_ord", allocationSize = 1, initialValue = 1)
	private Long id;

	@CPF(message = "cpf inválido")
	private String cpf;

	@Column(name = "valor_investimento")
	private Double valorInvestimento;

	@Column(name = "qtd_empresa")
	private Long numeroEmpresa;

	private Double troco;

	public Ordem() {
	}

	public Ordem(Long id, @CPF(message = "cpf inválido") String cpf, Double valorInvestimento, Long numeroEmpresa,
			Double troco) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.valorInvestimento = valorInvestimento;
		this.numeroEmpresa = numeroEmpresa;
		this.troco = troco;
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

	public Double getTroco() {
		return troco;
	}

	public void setTroco(Double troco) {
		this.troco = troco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getNumeroEmpresa() {
		return numeroEmpresa;
	}

	public void setNumeroEmpresa(Long numeroEmpresa) {
		this.numeroEmpresa = numeroEmpresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordem other = (Ordem) obj;
		return Objects.equals(id, other.id);
	}

}
