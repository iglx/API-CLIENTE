package net.atos.cliente.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Cliente {
	
	private Long id;
	
	@NotNull(message="Campo: cadastroData não pode ser nulo")
	private LocalDate dataCadastro;
	
	@NotNull(message="Campo: DataAlteracao não pode ser nulo")
	private LocalDateTime dataAlteracao;
	
	@NotNull(message="status do cliente não pode ser nulo")
	private Status status;
	
	@NotNull(message="nome não pode ser nulo")
	private String nome;
	@NotNull(message="cpf não pode ser nulo")
	private String cpf;
	@NotNull(message="e-mail não pode ser nulo")
	private String email;
	@NotNull(message="nascimento não pode ser nulo")
	private String nascimento;
	
	@NotNull(message = "Pelo menos um endereço deve ser cadastrado")
	@Valid
	private List<Endereco> enderecos;
	
	@NotNull(message = "Pelo menos um número de contato deve ser cadastrado")
	@Size(min = 1, message = "Pelo menos um número de contato deve ser cadastrado")
	@Valid
	private List<Contato> contatos;
	
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDateTime dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	
	public void addEndereco(Endereco endereco) {
		List<Endereco> enderecosLocal =
				Optional.ofNullable(this.getEnderecos()).orElseGet(() -> new ArrayList());
		enderecosLocal.add(endereco);
		
		this.enderecos = enderecosLocal;
	}

	public void addContato(Contato contato) {
		List<Contato> contatosLocal =
				Optional.ofNullable(this.getContatos()).orElseGet(() -> new ArrayList());
		contatosLocal.add(contato);
		
		this.contatos = contatosLocal;
	}
	
}
