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

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.ContatoVO;
import net.atos.cliente.domain.EnderecoVO;
import net.atos.cliente.domain.StatusEnum;
import net.atos.cliente.domain.TipoContatoEnum;
import net.atos.cliente.domain.TipoPessoaEnum;
import net.atos.cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CadastrarClienteTest {

	private CadastrarCliente cadastrarCliente;
	
	private Validator validator;
	private ClienteRepository pessoaMockitoRepository;
	
	@BeforeAll
	public void iniciarTesteGeral() {
		ValidatorFactory validatorFactor =
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarTeste() {
		
		this.pessoaMockitoRepository = Mockito.mock(ClienteRepository.class);
		
	//	this.cadastrarCliente = Mockito.mock(CadastrarCliente.class);
		
		cadastrarCliente = new CadastrarCliente(validator, pessoaMockitoRepository);
	}
	
	@Test
	@DisplayName("Testa o Cadastro quando for nulo")
	public void test_cadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = null;
		
		var assertThrows = assertThrows(IllegalArgumentException.class, () ->
			cadastrarCliente.persistir(pessoaVO));

		assertNotNull(assertThrows);
	}
	
	@Test
	@DisplayName("Testa os campos obrigatórios")
	public void test_camposCadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = new PessoaVO();
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
			cadastrarCliente.persistir(pessoaVO));
		
		assertEquals(10, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"Campo: cadastroData não pode ser nulo",
				"Campo: DataAlteracao não pode ser nulo",
				"status do cliente não pode ser nulo",
				"nome não pode ser nulo",
				"cpf não pode ser nulo",
				"e-mail não pode ser nulo",
				"nascimento não pode ser nulo",
				"Pelo menos um endereço deve ser cadastrado",
				"Pelo menos um número de contato deve ser cadastrado"
				));
	}
	
	@Test
	@DisplayName("endereço não cadastrado")
	public void test_endereco_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatus(StatusEnum.INATIVO);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setCpf("000.000.000-99");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");

		EnderecoVO endereco = new EnderecoVO();
		pessoaVO.setEndereco(endereco);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
		cadastrarCliente.persistir(pessoaVO));
		
		assertEquals(8, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"logradouro não pode ser nulo",
				"bairro não pode ser nulo",
				"cidade não pode ser nulo",
				"estado não pode ser nulo",
				"cep não pode ser nulo",
				"complemento não pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("contato não cadastrado")
	public void test_contato_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatus(StatusEnum.INATIVO);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setCpf("000.000.000-99");
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
		cadastrarCliente.persistir(pessoaVO));
		
		assertEquals(3, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"tipo de contato do cliente não pode ser nulo",
				"número não pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("Campo data de cadastro atual")
	public void test_data_diferente_lancaExcecao() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now().minusDays(1l));
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatus(StatusEnum.INATIVO);
		pessoaVO.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setCpf("000.000.000-99");
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

		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(pessoaVO));
		
		assertEquals("A data do cadastro deve ser atual", assertThrows.getMessage());

	}
	
	@Test
	@DisplayName("Teste de persistência do cadastro")
	public void test_dados_preenchidos_cadastroCriado() {
		
		assertNotNull(cadastrarCliente);
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now());
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatus(StatusEnum.INATIVO);
		pessoaVO.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setCpf("000.000.000-99");
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
		
		cadastrarCliente.persistir(pessoaVO);
		
		then(pessoaMockitoRepository).should(times(1)).save(any());
	}

}
