package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import net.atos.api.cliente.domain.ContatoVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoContatoEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;



@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuscaPessoaServiceTest {

	private BuscaPessoaService buscaPessoaService;
	
	private Validator validator;
	
	private PessoaRepository pessoaRepository;
	
	private Pageable pageable;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();	
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.pessoaRepository = Mockito.mock(PessoaRepository.class);
		this.pageable = Mockito.mock(Pageable.class);
		this.buscaPessoaService = new BuscaPessoaService(validator, pessoaRepository);	
	}
	
	
	
//	@Test	
//	@DisplayName("Testa consultas de clientes.")	
//	public void test_EncontraClientes_retornaLista(){
//		
//		PessoaVO pessoa =  new PessoaVO();
//		pessoa.setId(null);
//		pessoa.setDataCadastro(LocalDate.now());
//		pessoa.setDataAlteracao(LocalDateTime.now());
//		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
//		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
//		pessoa.setNome("Adolfo teste");
//		pessoa.setEmail("adolfo@gmail.com");
//		pessoa.setNascimento("20/10/1980");
//		pessoa.setNrCpf("111111111");
//		pessoa.setNrCnpj(null);
//		
//		EnderecoVO endereco = new EnderecoVO();
//		endereco.setLogradouro("Rua do Teste");
//		endereco.setBairro("Torre");
//		endereco.setCidade("Recife");
//		endereco.setCep("5000000");
//		endereco.setEstado("PE");
//		endereco.setComplemento("1 Andar");
//		
//		pessoa.setEndereco(endereco);
//		
//		ContatoVO contato = new ContatoVO();
//		contato.setTipoContato(TipoContatoEnum.CELULAR);
//		contato.setNumero("(81) 9999999");
//		pessoa.addContato(contato);
//		
//		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
//		contato.setNumero("(81) 3333333");
//		pessoa.addContato(contato);
//		
//		assertNotNull(this.buscaPessoaService);
//				
//		List<PessoaVO> pessoaTreinadas = new ArrayList<>();
//	//	pessoaTreinadas.add(pessoa);
//		Page<PessoaVO> pessoaPaginadas = new PageImpl<PessoaVO>(pessoaTreinadas, this.pageable,01);
//		
////		when(this.buscaPessoaService.porTodos(pageable))
////					.thenReturn(pessoaPaginadas);
//
//		Page<PessoaVO> pessoasEncontradas = pessoaPaginadas;
//		
//		
//		assertNotNull(List.of(pessoasEncontradas));
////		assertEquals(3, notasEncontradas.getSize());
//		
//	}
//	
//	@Test	
//	@DisplayName("Testa consultas vazias de clientes por periodo data cadastro.")	
//	public void test_quandoNaoEncontraClientePorPeriodoDTCad_lancaExcessao(){
//		
//		assertNotNull(this.buscaPessoaService);
//		LocalDate dataInicio = LocalDate.now().minusDays(10l);
//		LocalDate dataFim = LocalDate.now();
//		
//		List<PessoaEntity> pessoaTreinadas = new ArrayList<>();
//		Page<PessoaEntity> pessoasPaginadas = new PageImpl<>(pessoaTreinadas,this.pageable,0l);
//		when(this.pessoaRepository.findByDataCadastroBetween(any(), any(), pageable))
//					.thenReturn(pessoasPaginadas);
//
//		var assertThrows = 
//				assertThrows(NotFoundException.class, 
//						()-> this.buscaPessoaService.porPeriodoDataCadastro(dataInicio, dataFim, this.pageable));
//		
//		assertNotNull(assertThrows);
//		assertEquals("Nenhuma cliente para o periodo informado", assertThrows.getMessage());
//	}

	@Test	
	@DisplayName("Testa consultas cliente por periodo data cadastro.")	
	public void test_quandoEncontraCliPorPeriodoDTCadastro_retornaLista(){
		
		assertNotNull(this.buscaPessoaService);
		LocalDate dataInicio = LocalDate.now().minusDays(10l);
		LocalDate dataFim = LocalDate.now();
		
		List<PessoaEntity> pessoaTreinadas = new ArrayList<>();
		pessoaTreinadas.add(new PessoaEntity());
		pessoaTreinadas.add(new PessoaEntity());
		pessoaTreinadas.add(new PessoaEntity());
		Page<PessoaEntity> notasPaginadas = new PageImpl<>(pessoaTreinadas,this.pageable,0l);
		
		when(this.pessoaRepository.findByDataCadastroBetween(any(),any(),pageable))
					.thenReturn(notasPaginadas);
		
		Page<PessoaVO> notasEncontradas = this.buscaPessoaService.porPeriodoDataCadastro(dataInicio, dataFim, this.pageable);
		
		then(this.pessoaRepository).should(times(1)).findByDataCadastroBetween(any(), any(),pageable);
		
		assertNotNull(notasEncontradas);
		assertEquals(3, notasEncontradas.getSize());
		
	}
	
	@Test	
	@DisplayName("Testa Quando não encontra cliente por Id")	
	public void test_quandoNaoEncontraNFPorID_lancaExcessao(){
		
		assertNotNull(this.buscaPessoaService);
		
		var assertThrows = 
				assertThrows(NotFoundException.class, 
						()-> this.buscaPessoaService.pessoaVOporId(3l));
		
		then(pessoaRepository).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Não encontrada o cliente com id = 3");
	}
	
}
