package net.atos.servicos.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ServicoVO {
	
	private Long id;
	@NotNull(message = "ID do cliente não pode ser NULL")
	private Long idCliente;
	@NotNull(message = "Valor não pode ser NULL")
	@Min(value = 1l)
	private BigDecimal valor;
	@NotNull(message = "Tipo de cliente não pode ser NULL")
	private String tipoCliente;
	@NotNull(message = "Data de emissão não pode ser NULL")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataEmissao;
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoVO other = (ServicoVO) obj;
		return Objects.equals(idCliente, other.idCliente);
	}
	
	
	
}
