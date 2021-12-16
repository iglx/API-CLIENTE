package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.events.PessoaInativaEvent;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class DeletarPessoaService {

	private Validator validator;
	private BuscaPessoaService buscaPessoaService;
	private ApplicationEventPublisher eventPublisher;
	private PessoaRepository pessoaRepository;
	
	public DeletarPessoaService(Validator validator, BuscaPessoaService buscaPessoaService,
			ApplicationEventPublisher eventPublisher,PessoaRepository pessoaRepository) {
		super();
		this.validator = validator;
		this.buscaPessoaService = buscaPessoaService;
		this.eventPublisher = eventPublisher;
		this.pessoaRepository = pessoaRepository;
	}
	
	@Transactional
	public void deletar(Long id) {
		
		PessoaEntity pessoaEncontrada = buscaPessoaService.pessoaEntityporId(id);
		
		pessoaRepository.deleteById(pessoaEncontrada.getId());
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setId(id);
		PessoaInativaEvent event = new PessoaInativaEvent(pessoaVO); 
		
		this.eventPublisher.publishEvent(event);	
	}


}
