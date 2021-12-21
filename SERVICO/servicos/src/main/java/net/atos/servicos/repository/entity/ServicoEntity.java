package net.atos.servicos.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TB_SERVICO", 
uniqueConstraints =  @UniqueConstraint(name = "ID_SERVICO_UNIQUE",columnNames = {"ID_CLIENTE"}))
public class ServicoEntity {
	
	@Id
	@Column(name = "ID_SERVICO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_servico")
	@SequenceGenerator(name = "sq_servico", sequenceName = "sequence_servico", allocationSize = 1, initialValue = 1)
	private Long id;
	
	@Column(name = "ID_CLIENTE")
	private Long idCliente;
	
	@Column(name = "VALOR")
	private BigDecimal valor;
	
	@Column(name = "TIPO_CLIENTE")
	private String tipoCliente;
	
	@Column(name = "DT_EMISSAO")
	private LocalDate dataEmissao;
	
	@Column(name = "CANCELADO")
	private boolean cancelado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	
}
