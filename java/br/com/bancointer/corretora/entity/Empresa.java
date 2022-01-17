package br.com.bancointer.corretora.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.bancointer.corretora.dto.StatusEmpresaEnum;

@Entity
@Table(name = "tb_empresa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Empresa implements Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6840352960820327547L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_emp")
	@SequenceGenerator(name = "sq_emp", sequenceName = "sequence_emp", allocationSize = 1, initialValue = 1)
	private Long id;

	@NotNull(message = "Campo: nome não pode ser nulo")
	private String nome;

	@NotNull(message = "Campo: ticker não pode ser nulo")
	private String ticker;

	@NotNull(message = "Campo: valor não pode ser nulo")
	private Double valor;

	@NotNull(message = "Campo: status não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private StatusEmpresaEnum status;

	public Empresa() {

	}

	public Empresa(Long id, @NotNull(message = "Campo: nome não pode ser nulo") String nome,
			@NotNull(message = "Campo: ticker não pode ser nulo") String ticker,
			@NotNull(message = "Campo: valor não pode ser nulo") Double valor,
			@NotNull(message = "Campo: status não pode ser nulo") StatusEmpresaEnum status) {
		super();
		this.id = id;
		this.nome = nome;
		this.ticker = ticker;
		this.valor = valor;
		this.status = status;
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
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	}

}
