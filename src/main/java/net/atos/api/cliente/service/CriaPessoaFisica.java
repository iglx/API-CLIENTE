package net.atos.api.cliente.service;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.TipoPessoaEnum;
import net.atos.api.cliente.factory.PessoaFactory;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class CriaPessoaFisica implements CriaPessoa{
	
	private Validator validator;
	
	private PessoaRepository pessoaRepository;
	
	public CriaPessoaFisica(Validator v, PessoaRepository repository) {
		this.validator = v;
		this.pessoaRepository = repository;
	}

	public PessoaVO persistir(@NotNull(message = "Pessoa não pode ser nula") PessoaVO pessoaVO) {
		
		Set<ConstraintViolation<PessoaVO>>
			validate = this.validator.validate(pessoaVO);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Operação inválida", validate);
		}
		
		if(!pessoaVO.getDataCadastro().isEqual(LocalDate.now())) {
			throw new BadRequestException("A data do cadastro deve ser atual");
		}
		
		if (ObjectUtils.isEmpty(pessoaVO.getContatos()) || pessoaVO.getContatos().size() < 1) {
			throw new BadRequestException("número de contato deve ser cadastrado");
		}
		
		PessoaEntity pessoaEntity = new PessoaFactory(pessoaVO).toEntity();

		pessoaRepository.save(pessoaEntity);
		
		pessoaVO.setId(pessoaEntity.getId());
		
		return pessoaVO;
		
	}

	@Override
	public Boolean isType(TipoPessoaEnum type) {
		
		return TipoPessoaEnum.FISICA.equals(type);
	}
	
}

