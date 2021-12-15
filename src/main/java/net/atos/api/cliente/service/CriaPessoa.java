package net.atos.api.cliente.service;

import javax.validation.constraints.NotNull;

import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;

public interface CriaPessoa{
	
	public PessoaVO persistir(@NotNull(message = "Cliente não pode ser nulo") PessoaVO pessoaVO); 

	public Boolean isType(TipoPessoaEnum type);
}
