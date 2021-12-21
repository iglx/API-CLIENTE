package net.atos.servicos.listern;

import net.atos.servicos.domain.CancelaServicoVO;
import net.atos.servicos.domain.PessoaVO;
import net.atos.servicos.service.CancelaServicoService;

public class ServicoCanceladoListern {
	
	private CancelaServicoService cancelaServicoService;
	
	public ServicoCanceladoListern(CancelaServicoService pCancelaServicoService) {
		this.cancelaServicoService = pCancelaServicoService;
	}
	
	public void executa(PessoaVO pessoa) {
		
		CancelaServicoVO cancelaServicoVO = new CancelaServicoVO();
		cancelaServicoVO.setIdCliente(pessoa.getId());
		
		this.cancelaServicoService.cancelaServico(cancelaServicoVO);
	}
	
}
