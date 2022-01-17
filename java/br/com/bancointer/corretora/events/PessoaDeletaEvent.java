package net.atos.api.cliente.events;

import net.atos.api.cliente.domain.PessoaVO;

public class PessoaDeletaEvent {
	
	private PessoaVO pessoa;
	
	public PessoaDeletaEvent(PessoaVO pPessoa) {
		this.pessoa = pPessoa;		
	}

	public PessoaVO getPessoa() {
		return pessoa;
	}
	
}
