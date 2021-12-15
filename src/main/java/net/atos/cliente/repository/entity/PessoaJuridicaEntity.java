package net.atos.cliente.repository.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import net.atos.cliente.domain.TipoPessoaEnum;

@Entity
@DiscriminatorValue("JURIDICA")
public class PessoaJuridicaEntity extends PessoaEntity{

	public PessoaJuridicaEntity() {
		super.setTipoPessoaEnum(TipoPessoaEnum.JURIDICA);
	}
	
	@Column(name = "NR_CNPJ")
	private String nrCnpj;	

	@Override	
	public void setTipoPessoaEnum(TipoPessoaEnum tipoPessoaEnum) {
		throw new IllegalArgumentException();
	}

	public String getNrCnpj() {
		return nrCnpj;
	}

	public void setNrCnpj(String nrCnpj) {
		this.nrCnpj = nrCnpj;
	}
	
}
