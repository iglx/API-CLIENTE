package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InativarPessoaServiceTest {


	private InativarPessoaService InativarPessoaService;
	private Validator validator;
	private BuscaPessoaService buscaPessoaService;
	private PessoaRepository pessoaRepository;
	
	private ApplicationEventPublisher eventPublisher;
	
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();	
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.pessoaRepository = Mockito.mock(PessoaRepository.class);
		this.buscaPessoaService = Mockito.mock(BuscaPessoaService.class);
		this.eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
		
		this.InativarPessoaService = 
				new InativarPessoaService(this.validator,
										this.buscaPessoaService,
										this.eventPublisher,
										this.pessoaRepository);
																						
	}

	@Test	
	@DisplayName("Testa Inativar cliente não encontrado por ID.")	
	public void test_quandoClienteNaoEncontradoPorID_lancaExcessao(){
		
		when(this.buscaPessoaService.pessoaEntityporId(anyLong()))
						.thenThrow(NotFoundException.class);	
		
		var assertThrows = assertThrows(NotFoundException.class,
				()-> this.InativarPessoaService.inativar(Long.valueOf(5l)));

		assertNotNull(assertThrows);
		
	}

	@Test	
	@DisplayName("Testa Inativar cliente com sucesso.")	
	public void test_quandoInativarCliente_comSucesso(){
		
		PessoaEntity pessoaTreinada = new PessoaEntity();
		pessoaTreinada.setId(3l);
		pessoaTreinada.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		
		when(this.buscaPessoaService.pessoaEntityporId(anyLong()))
				.thenReturn(pessoaTreinada);
		
		this.InativarPessoaService.inativar(pessoaTreinada.getId());
		
		assertEquals(StatusPessoaEnum.INATIVO, pessoaTreinada.getStatusPessoaEnum());
		
	}
	
}
