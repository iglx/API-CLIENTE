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
public class InativarPessoaService {

	private Validator validator;
	private BuscaPessoaService buscaPessoaService;
	private ApplicationEventPublisher eventPublisher;
	private PessoaRepository pessoaRepository;
	
	public InativarPessoaService(Validator validator, BuscaPessoaService buscaPessoaService,
			ApplicationEventPublisher eventPublisher,PessoaRepository pessoaRepository) {
		super();
		this.validator = validator;
		this.buscaPessoaService = buscaPessoaService;
		this.eventPublisher = eventPublisher;
		this.pessoaRepository = pessoaRepository;
	}

	@Transactional
	public void inativar(Long id) {
		
		PessoaEntity pessoaEncontrada = buscaPessoaService.pessoaEntityporId(id);
		
		if (pessoaEncontrada.getStatusPessoaEnum().equals(StatusPessoaEnum.INATIVO)) {
			throw new BadRequestException("O cliente já está inativo.");	
		}
		
		pessoaEncontrada.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setId(id);
		PessoaInativaEvent event = new PessoaInativaEvent(pessoaVO); 
		
		this.eventPublisher.publishEvent(event);	
	}

}
