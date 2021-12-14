package net.atos.cliente.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.factory.ClienteFactory;
import net.atos.cliente.repository.ClienteRepository;
import net.atos.cliente.repository.entity.PessoaEntity;

@Service
public class BuscaClienteService {	
	
	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	public BuscaClienteService(Validator v, ClienteRepository repository) {
		this.validator = v;		
		this.clienteRepository = repository; 	
	}


	public Page<PessoaVO>  porPeriodoDataCadastro(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
		
		Page<PessoaEntity> clienteEntities = 
				clienteRepository.findByDataCadastroBetween(dataInicio,dataFim, pageable);
		
		if(clienteEntities.isEmpty()) {
			throw new NotFoundException("Nenhuma nota fiscal para o periodo informado");	
		}
		
		
		return new PageImpl<>(clienteEntities.getContent().stream()
				.map(ClienteFactory::new)
				.map(ClienteFactory::toVO)
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
		
		return new ClienteFactory(clienteEntity).toVO();
		
	}

		
	
}
