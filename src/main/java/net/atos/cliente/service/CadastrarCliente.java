package net.atos.cliente.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import net.atos.cliente.domain.Cliente;

@Service
public class CadastrarCliente {
	
	private Validator validator;
	
	public CadastrarCliente(Validator v) {
		this.validator = v;
	}

	public void persistir(@NotNull(message = "Cadastro não pode ser null") Cliente cliente) {
		
		Set<ConstraintViolation<Cliente>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Operação inválida", validate);
		}
		
		if(!cliente.getDataCadastro().isEqual(LocalDate.now())) {
			throw new BadRequestException("A data do cadastro deve ser atual");
		}
	}
	
}
