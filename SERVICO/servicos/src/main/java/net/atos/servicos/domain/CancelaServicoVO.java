package net.atos.servicos.domain;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class CancelaServicoVO {
	
	@NotNull(message = "ID do cliente não pode ser NULL")
	private Long idCliente;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
		CancelaServicoVO other = (CancelaServicoVO) obj;
		return Objects.equals(idCliente, other.idCliente);
	}
	
	
	
}
