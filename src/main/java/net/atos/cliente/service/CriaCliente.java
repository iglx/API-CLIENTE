package net.atos.cliente.service;

import javax.validation.constraints.NotNull;

import net.atos.cliente.domain.Cliente;
import net.atos.cliente.domain.StatusEnum;

public interface CriaCliente{
	
	public Cliente persistir(@NotNull(message = "Nota Fiscal não pode ser null") Cliente cliente); 

	public boolean isType(StatusEnum type);
}
