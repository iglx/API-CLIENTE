package net.atos.api.cliente.events;

import net.atos.api.cliente.domain.PessoaVO;

public class PessoaInativaEvent {
	
	private PessoaVO pessoa;
	
	public PessoaInativaEvent(PessoaVO pPessoa) {
		this.pessoa = pPessoa;		
	}

	public PessoaVO getPessoa() {
		return pessoa;
	}
		
}
