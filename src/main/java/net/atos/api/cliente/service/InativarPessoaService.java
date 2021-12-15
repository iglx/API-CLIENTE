package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.events.PessoaInativaEvent;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class InativarPessoaService {

	private Validator validator;
	private BuscaPessoaService buscaPessoaService;
	private ApplicationEventPublisher eventPublisher;


	@Transactional
	public void inativar(Long id) {
		
		PessoaEntity pessoaEncontrada = this.buscaPessoaService.entityPorId(id);
		
		if (pessoaEncontrada.getStatus().equals(StatusPessoaEnum.INATIVO)) {
			throw new BadRequestException("O cliente já está inativo.");	
		}
		
		pessoaEncontrada.setStatus(StatusPessoaEnum.INATIVO);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setId(id);
		PessoaInativaEvent event = new PessoaInativaEvent(pessoaVO); 
		
		this.eventPublisher.publishEvent(event);	
	}

}
