package net.atos.cliente.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.atos.cliente.domain.StatusEnum;
import net.atos.cliente.domain.TipoPessoaEnum;

@Entity
@Table(name = "TB_CLIENTE")
public class PessoaEntity {
	
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
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@Column(name = "TIPOPESSOA")
	@NotNull(message="Tipo Pessoa do cliente não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private TipoPessoaEnum tipoPessoaEnum;
	
	@Column(name = "NOME")
	@NotNull(message="nome não pode ser nulo")
	private String nome;
	
	@Column(name = "CPF")
	@NotNull(message="cpf não pode ser nulo")
	private String cpf;
	
	@Column(name = "EMAIL")
	@NotNull(message="e-mail não pode ser nulo")
	private String email;
	
	@Column(name = "NASCIMENTO")
	@NotNull(message="nascimento não pode ser nulo")
	private String nascimento;
	
	@NotNull(message = "Pelo menos um endereço deve ser cadastrado")
	@Size(min = 1, message = "Pelo menos um endereço deve ser cadastrado")
	@Valid
	@OneToOne
	@JoinColumn(name = "ID_CADASTRO",insertable = false, updatable = false)
	private EnderecoEntity enderecos;
	
	@NotNull(message = "Pelo menos um número de contato deve ser cadastrado")
	@Size(min = 1, message = "Pelo menos um número de contato deve ser cadastrado")
	@Valid
	@OneToMany(mappedBy = "id.cliente")
	private List<ContatoEntity> contatos;
	
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
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
	
	public EnderecoEntity getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(EnderecoEntity endereco) {
		this.enderecos = endereco;
	}
	
	

	public TipoPessoaEnum getTipoPessoaEnum() {
		return tipoPessoaEnum;
	}

	public void setTipoPessoaEnum(TipoPessoaEnum tipoPessoaEnum) {
		this.tipoPessoaEnum = tipoPessoaEnum;
	}

	public List<ContatoEntity> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoEntity> contatos) {
		this.contatos = contatos;
	}

	public void addContato(ContatoEntity contato) {
		List<ContatoEntity> contatosLocal =
				Optional.ofNullable(this.getContatos()).orElseGet(() -> new ArrayList());
		contatosLocal.add(contato);
		
		this.contatos = contatosLocal;
	}

}
