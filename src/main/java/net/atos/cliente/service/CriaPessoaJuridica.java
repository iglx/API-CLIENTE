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

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.TipoPessoaEnum;
import net.atos.cliente.factory.ClienteFactory;
import net.atos.cliente.repository.ClienteRepository;
import net.atos.cliente.repository.entity.PessoaEntity;

@Service
public class CriaPessoaJuridica implements CriaPessoa{
	
	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public CriaPessoaJuridica(Validator v, ClienteRepository repository) {
		this.validator = v;
		this.clienteRepository = repository;
	}

	public PessoaVO persistir(@NotNull(message = "Cadastro não pode ser null") PessoaVO cliente) {
		
		Set<ConstraintViolation<PessoaVO>>
			validate = this.validator.validate(cliente);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Operação inválida", validate);
		}
		
		if(!cliente.getDataCadastro().isEqual(LocalDate.now())) {
			throw new BadRequestException("A data do cadastro deve ser atual");
		}
		
		if (ObjectUtils.isEmpty(cliente.getContatos()) || cliente.getContatos().size() < 1) {
			throw new BadRequestException("número de contato deve ser cadastrado");
		}
		
		PessoaEntity clienteEntity = new ClienteFactory(cliente).toEntity();

		clienteRepository.save(clienteEntity);
		
		cliente.setId(clienteEntity.getId());
		
		return cliente;
		
	}

	@Override
	public Boolean isType(TipoPessoaEnum type) {
		
		return TipoPessoaEnum.JURIDICA.equals(type);
	}
	
}

