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

import net.atos.cliente.domain.Cliente;
import net.atos.cliente.domain.Status;
import net.atos.cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CadastrarClienteTest {

	private CadastrarCliente cadastrarCliente;
	
	private Validator validator;
	private ClienteRepository clienteRepository;
	
	@BeforeAll
	public void iniciarTesteGeral() {
		ValidatorFactory validatorFactor =
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarTeste() {
		
		this.clienteRepository = Mockito.mock(ClienteRepository.class);
		
		cadastrarCliente = new CadastrarCliente(validator, clienteRepository);
	}
	
	@Test
	@DisplayName("Testa o Cadastro quando for nulo")
	public void test_cadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = null;
		
		var assertThrows = assertThrows(IllegalArgumentException.class, () ->
			cadastrarCliente.persistir(cliente));

		assertNotNull(assertThrows);
	}
	
	@Test
	@DisplayName("Testa os campos obrigatórios")
	public void test_camposCadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		
		var assertThrows = assertThrows(ConstraintViolationException.class, () ->
			cadastrarCliente.persistir(cliente));
		
		assertEquals(15, assertThrows.getConstraintViolations().size());
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
				"telefone não pode ser nulo",
				"celular não pode ser nulo",
				"nascimento não pode ser nulo",
				"logradouro não pode ser nulo",
				"bairro não pode ser nulo",
				"cidade não pode ser nulo",
				"estado não pode ser nulo",
				"cep não pode ser nulo",
				"complemento não pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("Campo data de cadastro atual")
	public void test_data_diferente_lancaExcecao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now().minusDays(1l));
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		cliente.setCpf("000.000.000-99");
		cliente.setEmail("teste@email.com.br");
		cliente.setTelefone("(99) 9999-9999");
		cliente.setCelular("(99) 9 9999-9999");
		cliente.setNascimento("01/01/1901");
		cliente.setLogradouro("Rua do Brasil, 1500");
		cliente.setBairro("Vila Brasil");
		cliente.setCidade("Java");
		cliente.setEstado("Brasil");
		cliente.setCep("01234-567");
		cliente.setComplemento("Clube dos Javeiros");
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("A data do cadastro deve ser atual", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste de persistência do cadastro")
	public void test_dados_preenchidos_cadastroCriado() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		cliente.setCpf("000.000.000-99");
		cliente.setEmail("teste@email.com.br");
		cliente.setTelefone("(99) 9999-9999");
		cliente.setCelular("(99) 9 9999-9999");
		cliente.setNascimento("01/01/1901");
		cliente.setLogradouro("Rua do Brasil, 1500");
		cliente.setBairro("Vila Brasil");
		cliente.setCidade("Java");
		cliente.setEstado("Brasil");
		cliente.setCep("01234-567");
		cliente.setComplemento("Clube dos Javeiros");
		
		cadastrarCliente.persistir(cliente);
		
		then(clienteRepository).should(times(1)).save(any());
	}

}
