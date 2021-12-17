package net.atos.api.cliente.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.cliente.repository.entity.PessoaJuridicaEntity;

@Repository
public interface PessoaJuridicaRepository extends CrudRepository<PessoaJuridicaEntity, Long>{
		
}