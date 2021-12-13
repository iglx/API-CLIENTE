package net.atos.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.StatusEnum;
import net.atos.cliente.repository.entity.PessoaEntity;

@Service
public class InativarClienteService {

	private Validator validator;
	private BuscaClienteService buscaClienteService;
	private ApplicationEventPublisher eventPublisher;


	@Transactional
	public void inativar(Long id) {
		
		PessoaEntity clienteEncontrado = this.buscaClienteService.entityPorId(id);
		
		if (clienteEncontrado.getStatus().equals(StatusEnum.INATIVO)) {
			throw new BadRequestException("O cliente já está inativo.");	
		}
		
		clienteEncontrado.setStatus(StatusEnum.INATIVO);
		
		PessoaVO cliente = new PessoaVO();
		cliente.setId(id);
		ClienteInativoEvent event = new ClienteInativoEvent(cliente); 
		
		this.eventPublisher.publishEvent(event);	
	}

}
