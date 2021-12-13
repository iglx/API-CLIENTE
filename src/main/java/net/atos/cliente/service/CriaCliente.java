package net.atos.cliente.service;

import javax.validation.constraints.NotNull;

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.StatusEnum;

public interface CriaCliente{
	
	public PessoaVO persistir(@NotNull(message = "Nota Fiscal não pode ser null") PessoaVO cliente); 

	public boolean isType(StatusEnum type);
}
