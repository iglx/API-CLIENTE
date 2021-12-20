package net.atos.cliente.service;

import javax.ws.rs.BadRequestException;

import net.atos.api.cliente.events.PessoaInativaEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class InativarClienteService {

	private BuscaClienteService buscaClienteService;
	private ApplicationEventPublisher eventPublisher;


	@Transactional
	public void inativar(Long id) {
		
		PessoaEntity clienteEncontrado = this.buscaClienteService.entityPorId(id);
		
		if (clienteEncontrado.getStatusPessoaEnum().equals(StatusPessoaEnum.INATIVO)) {
			throw new BadRequestException("O cliente já está inativo.");	
		}
		
		clienteEncontrado.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		
		PessoaVO cliente = new PessoaVO();
		cliente.setId(id);
		PessoaInativaEvent event = new PessoaInativaEvent(cliente);
		
		this.eventPublisher.publishEvent(event);	
	}
}
