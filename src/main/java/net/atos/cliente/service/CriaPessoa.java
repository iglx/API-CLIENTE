package net.atos.cliente.service;

import javax.validation.constraints.NotNull;

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.StatusEnum;
import net.atos.cliente.domain.TipoPessoaEnum;

public interface CriaPessoa{
	
	public PessoaVO persistir(@NotNull(message = "Cliente Tipo não pode ser null") PessoaVO pessoaVO); 

	public Boolean isType(TipoPessoaEnum type);
}
