package net.atos.api.cliente.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.factory.PessoaFactory;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class BuscaPessoaService {	
	
	private Validator validator;
	
	private PessoaRepository pessoaRepository;
	
	public BuscaPessoaService(Validator pValidator, PessoaRepository pessoaRepository) {
		this.validator = pValidator;		
		this.pessoaRepository = pessoaRepository; 	
	}
	
public Page<PessoaVO>  porTodos(Pageable pageable) {
		
		Page<PessoaEntity> pessoaEntities = 
				pessoaRepository.findAll(pageable);
		
		if(pessoaEntities.isEmpty()) {
			throw new NotFoundException("Nenhum cliente para o periodo informado");	
		}
		
		
		return new PageImpl<>(pessoaEntities.getContent().stream()
				.map(PessoaFactory::new)
				.map(PessoaFactory::toVO)
				.collect(Collectors.toList()),
				pessoaEntities.getPageable(),
				pessoaEntities.getTotalElements());		     	
	}

	public Page<PessoaVO>  porPeriodoDataCadastro(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
		
		Page<PessoaEntity> pessoaEntities = 
				pessoaRepository.findByDataCadastroBetween(dataInicio,dataFim, pageable);
		
		if(pessoaEntities.isEmpty()) {
			throw new NotFoundException("Nenhum cliente para o periodo informado");	
		}
		
		
		return new PageImpl<>(pessoaEntities.getContent().stream()
				.map(PessoaFactory::new)
				.map(PessoaFactory::toVO)
				.collect(Collectors.toList()),
				pessoaEntities.getPageable(),
				pessoaEntities.getTotalElements());		     	
	}

	public PessoaVO pessoaVOporId(long id) {
		PessoaEntity pessoaEntity = this.pessoaRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("N??o encontrada o cliente com id = "+id));
		
		return new PessoaFactory(pessoaEntity).toVO();
		
	}
	
	public PessoaEntity pessoaEntityporId(long id) {
		PessoaEntity pessoaEntity = this.pessoaRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("N??o encontrada o cliente com id = "+id));
		
		return new PessoaFactory(pessoaEntity).toEntity();
		
	}

}
