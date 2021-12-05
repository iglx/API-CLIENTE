package net.atos.cliente.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.atos.cliente.domain.Status;

@Entity
@Table(name = "TB_CLIENTE")
public class ClienteEntity {
	
	@Id
	@Column(name = "ID_CADASTRO")
	private Long id;
	
	@Column(name = "DT_CADASTRO")
	@NotNull(message="Campo: cadastroData não pode ser nulo")
	private LocalDate dataCadastro;
	
	@Column(name = "DT_ALTERACAO")
	@NotNull(message="Campo: DataAlteracao não pode ser nulo")
	private LocalDateTime dataAlteracao;
	
	@Column(name = "STATUS")
	@NotNull(message="status do cliente não pode ser nulo")
	private Status status;
	
	@Column(name = "NOME")
	@NotNull(message="nome não pode ser nulo")
	private String nome;
	
	@Column(name = "CPF")
	@NotNull(message="cpf não pode ser nulo")
	private String cpf;
	
	@Column(name = "EMAIL")
	@NotNull(message="e-mail não pode ser nulo")
	private String email;
	
	@Column(name = "TELEFONE")
	@NotNull(message="telefone não pode ser nulo")
	private String telefone;
	
	@Column(name = "CELULAR")
	@NotNull(message="celular não pode ser nulo")
	private String celular;
	
	@Column(name = "NASCIMENTO")
	@NotNull(message="nascimento não pode ser nulo")
	private String nascimento;
	
	@Column(name = "LOGRADOURO")
	@NotNull(message="logradouro não pode ser nulo")
	private String logradouro;
	
	@Column(name = "BAIRRO")
	@NotNull(message="bairro não pode ser nulo")
	private String bairro;
	
	@Column(name = "CIDADE")
	@NotNull(message="cidade não pode ser nulo")
	private String cidade;
	
	@Column(name = "ESTADO")
	@NotNull(message="estado não pode ser nulo")
	private String estado;
	
	@Column(name = "CEP")
	@NotNull(message="cep não pode ser nulo")
	private String cep;
	
	@Column(name = "COMPLEMENTO")
	@NotNull(message="complemento não pode ser nulo")
	private String complemento;
	
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
