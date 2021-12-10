package net.atos.cliente.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import net.atos.cliente.domain.Cliente;
import net.atos.cliente.repository.ClienteRepository;
import net.atos.cliente.repository.entity.ClienteEntity;

@Service
public class CadastrarCliente {
	
	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public CadastrarCliente(Validator v, ClienteRepository repository) {
		this.validator = v;
		this.clienteRepository = repository;
	}

	public Cliente persistir(@NotNull(message = "Cadastro não pode ser null") Cliente cliente) {
		
		Set<ConstraintViolation<Cliente>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Operação inválida", validate);
		}
		
		if(!cliente.getDataCadastro().isEqual(LocalDate.now())) {
			throw new BadRequestException("A data do cadastro deve ser atual");
		}
		
		if (ObjectUtils.isEmpty(cliente.getItens()) || cliente.getItens().size() < 1) {
			throw new BadRequestException("número de contato deve ser cadastrado");
		}
		
	//	cliente.getItens().forEach(item -> this.validaItem(item));
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setDataCadastro(cliente.getDataCadastro());
		clienteEntity.setDataAlteracao(cliente.getDataAlteracao());
		clienteEntity.setStatus(cliente.getStatus());
		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setEmail(cliente.getEmail());

		clienteEntity.setNascimento(cliente.getNascimento());
		clienteEntity.setLogradouro(cliente.getLogradouro());
		clienteEntity.setBairro(cliente.getBairro());
		clienteEntity.setCidade(cliente.getCidade());
		clienteEntity.setEstado(cliente.getEstado());
		clienteEntity.setCep(cliente.getCep());
		clienteEntity.setComplemento(cliente.getComplemento());
		
		clienteRepository.save(clienteEntity);
		
		cliente.setId(clienteEntity.getId());
		
		return cliente;
	
	}
	
	/*
	private void validaItem(Item item) {
		Optional.ofNullable(item.getTelefone())
		.orElseThrow(() -> new BadRequestException("O número de telefone deve ser cadastrado"));
		
		Optional.ofNullable(item.getCelular())
		.orElseThrow(() -> new BadRequestException("O celular deve ser cadastrado"));
	}
	*/
}

