package net.atos.api.cliente.repository.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;

@Entity
@Table(name = "TB_CLIENTE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO_PESSOA", 
	discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PESSOA")
public class PessoaEntity implements Serializable {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6840352960820327547L;
	
	@Id
	@Column(name = "ID_CADASTRO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cli")	
	@SequenceGenerator(name = "sq_cli",sequenceName = "sequence_cli",
    		allocationSize = 1,
    		initialValue = 1)
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
	private StatusPessoaEnum statusPessoaEnum;
	
	@Column(name = "TIPO_PESSOA", insertable=false, updatable=false)
	@NotNull(message="Tipo Pessoa do cliente não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private TipoPessoaEnum tipoPessoaEnum;
	
	@Column(name = "NOME")
	@NotNull(message="nome não pode ser nulo")
	private String nome;
	
	@Column(name = "EMAIL")
	@NotNull(message="e-mail não pode ser nulo")
	private String email;
	
	@Column(name = "NASCIMENTO")
	@NotNull(message="nascimento não pode ser nulo")
	private String nascimento;
	
	@NotNull(message = "Pelo menos um endereço deve ser cadastrado")
	@Valid
	@OneToOne(mappedBy = "pessoa",cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private EnderecoEntity endereco;
	
	@NotNull(message = "Pelo menos um número de contato deve ser cadastrado")
	@Size(min = 1, message = "Pelo menos um número de contato deve ser cadastrado")
	@Valid
	@OneToMany(mappedBy = "id.pessoa", cascade = CascadeType.ALL)
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
	
	public EnderecoEntity getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoEntity endereco) {
		endereco.setPessoa(this);
		this.endereco = endereco;
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
