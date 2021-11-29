package net.atos.cliente.service;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import net.atos.cliente.domain.Cliente;

@Service
public class CadastrarCliente {
	public void persistir(Cliente cliente) {
		
		Optional.ofNullable(cliente.getDataCadastro())
				.orElseThrow(() -> new BadRequestException("Campo: cadastroData não pode ser nulo"));
		Optional.ofNullable(cliente.getDataAlteracao())
				.orElseThrow(() -> new BadRequestException("Campo: DataAlteracao não pode ser nulo"));
		Optional.ofNullable(cliente.getStatus())
				.orElseThrow(() -> new BadRequestException("status do cliente não pode ser nulo"));
		Optional.ofNullable(cliente.getNome())
				.orElseThrow(() -> new BadRequestException("nome não pode ser nulo"));
		Optional.ofNullable(cliente.getCpf())
				.orElseThrow(() -> new BadRequestException("cpf não pode ser nulo"));
		Optional.ofNullable(cliente.getEmail())
				.orElseThrow(() -> new BadRequestException("email não pode ser nulo"));
		Optional.ofNullable(cliente.getTelefone())
				.orElseThrow(() -> new BadRequestException("telefone não pode ser nulo"));
		Optional.ofNullable(cliente.getCelular())
				.orElseThrow(() -> new BadRequestException("celular não pode ser nulo"));
		Optional.ofNullable(cliente.getNascimento())
				.orElseThrow(() -> new BadRequestException("nascimento não pode ser nulo"));

	}
}
