package net.atos.cliente.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.cliente.domain.ContatoVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoContatoEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.service.CriaPessoaFisica;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CadastrarPessoaTest {

	private CriaPessoaFisica cadastrarPessoa;
	
	private Validator validator;
	private PessoaRepository pessoaMockitoRepository;
	
	@BeforeAll
	public void iniciarTesteGeral() {
		ValidatorFactory validatorFactor =
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarTeste() {
		
		this.pessoaMockitoRepository = Mockito.mock(PessoaRepository.class);
		
	//	this.cadastrarCliente = Mockito.mock(CadastrarCliente.class);
		
		cadastrarPessoa = new CriaPessoaFisica(validator, pessoaMockitoRepository);
	}
	
	@Test
	@DisplayName("Testa o cliente quando for nulo")
	public void test_cadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = null;
		
		var assertThrows = assertThrows(IllegalArgumentException.class, () ->
			cadastrarPessoa.persistir(pessoaVO));

		assertNotNull(assertThrows);
	}
	
	@Test
	@DisplayName("Testa os campos obrigat??rios")
	public void test_camposCadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = new PessoaVO();
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
			cadastrarPessoa.persistir(pessoaVO));
		
		assertEquals(9, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"Campo: cadastroData n??o pode ser nulo",
				"Campo: DataAlteracao n??o pode ser nulo",
				"status do cliente n??o pode ser nulo",
				"nome n??o pode ser nulo",
				"e-mail n??o pode ser nulo",
				"nascimento n??o pode ser nulo",
				"Pelo menos um endere??o deve ser cadastrado",
				"Pelo menos um n??mero de contato deve ser cadastrado"
				));
	}
	
	@Test
	@DisplayName("endere??o n??o cadastrado")
	public void test_endereco_null_lancarExcessao() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");

		EnderecoVO endereco = new EnderecoVO();
		pessoaVO.setEndereco(endereco);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
		cadastrarPessoa.persistir(pessoaVO));
		
		assertEquals(8, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"logradouro n??o pode ser nulo",
				"bairro n??o pode ser nulo",
				"cidade n??o pode ser nulo",
				"estado n??o pode ser nulo",
				"cep n??o pode ser nulo",
				"complemento n??o pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("contato n??o cadastrado")
	public void test_contato_null_lancarExcessao() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");

		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Brasil, 1500");
		endereco.setBairro("Vila Brasil");
		endereco.setCidade("Java");
		endereco.setEstado("Brasil");
		endereco.setCep("01234-567");
		endereco.setComplemento("Clube dos Javeiros");
		pessoaVO.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO(); 
		pessoaVO.addContato(contato);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
		cadastrarPessoa.persistir(pessoaVO));
		
		assertEquals(3, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"tipo de contato do cliente n??o pode ser nulo",
				"n??mero n??o pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("Campo data de cadastro atual")
	public void test_data_diferente_lancaExcecao() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now().minusDays(1l));
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		pessoaVO.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");

		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Brasil, 1500");
		endereco.setBairro("Vila Brasil");
		endereco.setCidade("Java");
		endereco.setEstado("Brasil");
		endereco.setCep("01234-567");
		endereco.setComplemento("Clube dos Javeiros");
		pessoaVO.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO(); 
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(99) 9 9999-9999");
		pessoaVO.addContato(contato);	

		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarPessoa.persistir(pessoaVO));
		
		assertEquals("A data do cadastro deve ser atual", assertThrows.getMessage());

	}
	
	@Test
	@DisplayName("Teste de persist??ncia do cadastro")
	public void test_dados_preenchidos_cadastroCriado() {
		
		assertNotNull(cadastrarPessoa);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		pessoaVO.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");

		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Brasil, 1500");
		endereco.setBairro("Vila Brasil");
		endereco.setCidade("Java");
		endereco.setEstado("Brasil");
		endereco.setCep("01234-567");
		endereco.setComplemento("Clube dos Javeiros");
		pessoaVO.setEndereco(endereco);
		
		
		ContatoVO contato = new ContatoVO(); 
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(99) 9 9999-9999");
		pessoaVO.addContato(contato);
		
		cadastrarPessoa.persistir(pessoaVO);
		
		then(pessoaMockitoRepository).should(times(1)).save(any());
	}

}
