package net.atos.api.cliente.events;

import net.atos.api.cliente.domain.PessoaVO;

public class PessoaCreatedEvent {
	
	private PessoaVO pessoa;
	
	public PessoaCreatedEvent(PessoaVO pPessoa) {
		this.pessoa = pPessoa;		
	}

	public PessoaVO getPessoa() {
		return pessoa;
	}
	
	

}
