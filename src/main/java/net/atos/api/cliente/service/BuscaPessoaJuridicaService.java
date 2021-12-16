package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.cliente.repository.PessoaFisicaRepository;
import net.atos.api.cliente.repository.PessoaJuridicaRepository;
import net.atos.api.cliente.repository.entity.PessoaJuridicaEntity;


@Service
public class BuscaPessoaJuridicaService {	
	
	private Validator validator;
	
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	public BuscaPessoaJuridicaService(Validator v, PessoaJuridicaRepository repository) {
		this.validator = v;		
		this.pessoaJuridicaRepository = repository; 	
	}

	public PessoaJuridicaEntity porId(Long id) {
		return this.pessoaJuridicaRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Não encontrado o cliente com id = "+id));		
	}
	
}
