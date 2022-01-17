package br.com.bancointer.corretora.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tb_ordem_empresa")
public class OrdemEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_ord")
	@SequenceGenerator(name = "sq_ord", sequenceName = "sequence_ord", allocationSize = 1, initialValue = 1)
	private Long id;

	private Long quantidade;

	@Column(name = "valor_total")
	private Double valorTotal;

	@Valid
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Ordem ordem;

	@Valid
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	// private List<Empresa> empresas;
	private Empresa empresa;

	public OrdemEmpresa() {
	}

	public OrdemEmpresa(Long id, Long quantidade, Double valorTotal, @Valid Ordem ordem, @Valid Empresa empresa) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.ordem = ordem;
		this.empresa = empresa;
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

	public Ordem getOrdem() {
		return ordem;
	}

	public void setOrdem(Ordem ordem) {
		ordem = ordem;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		OrdemEmpresa other = (OrdemEmpresa) obj;
		return Objects.equals(id, other.id);
	}

}
