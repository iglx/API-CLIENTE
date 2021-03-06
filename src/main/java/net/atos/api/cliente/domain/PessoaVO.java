package net.atos.api.cliente.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PessoaVO {
	
	private Long id;
	
	@NotNull(message="Campo: cadastroData não pode ser nulo")
	private LocalDate dataCadastro;
	
	@NotNull(message="Campo: DataAlteracao não pode ser nulo")
	private LocalDateTime dataAlteracao;
	
	@NotNull(message="status do cliente não pode ser nulo")
	private StatusPessoaEnum statusPessoaEnum;
	
	@NotNull(message = "Campo TipoPessoa não pode ser nulo")
	private TipoPessoaEnum tipoPessoaEnum;
	
	@NotNull(message="nome não pode ser nulo")
	private String nome;
	
	@NotNull(message="e-mail não pode ser nulo")
	private String email;
	
	@NotNull(message="nascimento não pode ser nulo")
	private String nascimento;
	
	private String nrCpf;
	
	private String nrCnpj;	
	
	@NotNull(message = "Pelo menos um endereço deve ser cadastrado")
	@Valid
	private EnderecoVO endereco;
	
	@NotNull(message = "Pelo menos um número de contato deve ser cadastrado")
	@Size(min = 1, message = "Pelo menos um número de contato deve ser cadastrado")
	@Valid
	private List<ContatoVO> contatos;
	
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

	public StatusPessoaEnum getStatusPessoaEnum() {
		return statusPessoaEnum;
	}

	public void setStatusPessoaEnum(StatusPessoaEnum statusPessoaEnum) {
		this.statusPessoaEnum = statusPessoaEnum;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public String getNrCpf() {
		return nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}

	public String getNrCnpj() {
		return nrCnpj;
	}

	public void setNrCnpj(String nrCnpj) {
		this.nrCnpj = nrCnpj;
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
	
	public EnderecoVO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoVO endereco) {
		this.endereco = endereco;
	}

	public List<ContatoVO> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoVO> contatos) {
		this.contatos = contatos;
	}
	
	public TipoPessoaEnum getTipoPessoaEnum() {
		return tipoPessoaEnum;
	}

	public void setTipoPessoaEnum(TipoPessoaEnum tipoPessoaEnum) {
		this.tipoPessoaEnum = tipoPessoaEnum;
	}

	public void addContato(ContatoVO contato) {
		List<ContatoVO> contatosLocal =
				Optional.ofNullable(this.getContatos()).orElseGet(() -> new ArrayList());
		contatosLocal.add(contato);
		
		this.contatos = contatosLocal;
	}
	
}
