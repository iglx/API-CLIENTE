package net.atos.cliente.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;
import net.atos.api.cliente.factory.PessoaFactory;
import net.atos.api.cliente.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.repository.entity.PessoaEntity;

@Service
public class BuscaClienteService {	
	
	private PessoaRepository clienteRepository;
	
	public BuscaClienteService(Validator v, PessoaRepository repository) {
		this.clienteRepository = repository;
	}


	public Page<PessoaVO>  porPeriodoDataCadastro(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
		
		Page<PessoaEntity> clienteEntities = 
				clienteRepository.findByDataCadastroBetween(dataInicio,dataFim, pageable);
		
		if(clienteEntities.isEmpty()) {
			throw new NotFoundException("Nenhuma nota fiscal para o periodo informado");	
		}
		
		
		return new PageImpl<>(clienteEntities.getContent().stream()
				.map(PessoaFactory::new)
				.map(PessoaFactory::toVO)
				.collect(Collectors.toList()),
				clienteEntities.getPageable(),
				clienteEntities.getTotalElements());		     	
	}

	public PessoaEntity entityPorId(long id) {
		return this.clienteRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Não encontrada o cliente com id = "+id));		
	}
	

	public PessoaVO pessoaVOporId(long id) {
		PessoaEntity clienteEntity = this.clienteRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Não encontrada o cliente com id = "+id));
		
		return new PessoaFactory(clienteEntity).toVO();
		
	}

		
	
}
