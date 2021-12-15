package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import net.atos.cliente.domain.TipoPessoaEnum;

@Entity
@DiscriminatorValue("FISICA")
public class PessoaFisicaEntity extends PessoaEntity{

	public PessoaFisicaEntity() {
		super.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
	}
	
	@Column(name = "NR_CPF")
	private String nrCpf;	

	@Override	
	public void setTipoPessoaEnum(TipoPessoaEnum tipoPessoaEnum) {
		throw new IllegalArgumentException();
	}

	public String getNrCpf() {
		return nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}
	
}
