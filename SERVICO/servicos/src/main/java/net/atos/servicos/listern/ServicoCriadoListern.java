package net.atos.servicos.listern;

import net.atos.servicos.domain.PessoaVO;
import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.service.CriaServicoService;

public class ServicoCriadoListern {
	
	private CriaServicoService criaServicoService;
	
	public ServicoCriadoListern(CriaServicoService pCriaServicoService) {
		this.criaServicoService = pCriaServicoService;
	}
	
	public void executa(PessoaVO pessoa) {
		
		ServicoVO servicoVO = new ServicoVO();
		servicoVO.setIdCliente(pessoa.getId());
		servicoVO.setDataEmissao(pessoa.getDataEmissao());
		servicoVO.setTipoCliente(pessoa.getTipoPessoaEnum());
		//servicoVO.setValor(pessoa.getValor());
		
		this.criaServicoService.criarServico(servicoVO);
	}
	
}
