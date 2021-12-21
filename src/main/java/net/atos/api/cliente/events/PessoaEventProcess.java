package net.atos.api.cliente.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import net.atos.api.cliente.domain.PessoaVO;

@Service
public class PessoaEventProcess {
	
	
	private RabbitTemplate rabbitTemplate;

	public PessoaEventProcess(RabbitTemplate pRabbitTemplate) {
		this.rabbitTemplate = pRabbitTemplate;
		
	}
	

	@Async
	@TransactionalEventListener
	public void handleEvent(PessoaCreatedEvent event) {
		
		PessoaVO pessoa = event.getPessoa();
		
		this.rabbitTemplate.convertAndSend("pessoa", 
				"cli.created.pessoa", pessoa);
	}
	
	@Async
	@TransactionalEventListener
	public void handleEvent(PessoaDeletaEvent event) {
		
		PessoaVO pessoa = event.getPessoa();
		
		this.rabbitTemplate.convertAndSend("cliente", 
				"cli.deleta.pessoa", pessoa);
	}
	
	@Async
	@TransactionalEventListener
	public void handleEvent(PessoaInativaEvent event) {
		
		PessoaVO pessoa = event.getPessoa();
		
		this.rabbitTemplate.convertAndSend("cliente", 
				"cli.inativa.pessoa", pessoa);
	}

}
