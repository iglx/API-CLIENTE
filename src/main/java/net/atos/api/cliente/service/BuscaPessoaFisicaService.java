package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.cliente.repository.PessoaFisicaRepository;
import net.atos.api.cliente.repository.entity.PessoaFisicaEntity;

@Service
public class BuscaPessoaFisicaService {	
	
	private Validator validator;
	
	private PessoaFisicaRepository pessoaRepository;
	
	public BuscaPessoaFisicaService(Validator v, PessoaFisicaRepository repository) {
		this.validator = v;		
		this.pessoaRepository = repository; 	
	}

	public PessoaFisicaEntity porId(Long id) {
		return  this.pessoaRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Não encontrado o cliente com id = "+id));		
	}
	
}
