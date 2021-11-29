package net.atos.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.atos.cliente.domain.Status;
import net.atos.cliente.domain.Cliente;

class CadastrarClienteTest {

	private CadastrarCliente cadastrarCliente;
	
	@BeforeEach
	public void iniciarTeste() {
		cadastrarCliente = new CadastrarCliente();
	}
	
	@Test
	@DisplayName("Teste do campo: data de cadastro")
	public void test_dataCadastro_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("Campo: cadastroData não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: data de alteração")
	public void test_dataAlteracao_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("Campo: DataAlteracao não pode ser nulo", assertThrows.getMessage());	
	}
	
	@Test
	@DisplayName("Teste do status do cliente")
	public void test_status_null_lancarExcessao() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("status do cliente não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: nome")
	public void nome_null() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("nome não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: cpf")
	public void cpf_null() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("cpf não pode ser nulo", assertThrows.getMessage());
	}

	@Test
	@DisplayName("Teste do campo: email")
	public void email_null() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		cliente.setCpf("000.000.000-99");
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("email não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: telefone")
	public void telefone_null() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		cliente.setCpf("000.000.000-99");
		cliente.setEmail("teste@email.com.br");
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("telefone não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: celular")
	public void celular_null() {
		
		assertNotNull(cadastrarCliente);
		
		Cliente cliente = new Cliente();
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDataAlteracao(LocalDateTime.now());
		cliente.setStatus(Status.INATIVO);
		cliente.setNome("Nome do cliente");
		cliente.setCpf("000.000.000-99");
		cliente.setEmail("teste@email.com.br");
		cliente.setTelefone("(99) 9999-9999");
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("celular não pode ser nulo", assertThrows.getMessage());
	}
	
	@Test
	@DisplayName("Teste do campo: nascimento")
	public void nascimento_null() {
		
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
		
		var assertThrows = assertThrows(BadRequestException.class, () -> cadastrarCliente.persistir(cliente));
		
		assertEquals("nascimento não pode ser nulo", assertThrows.getMessage());
	}

}
