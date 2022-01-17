package net.atos.api.cliente.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.cliente.repository.entity.PessoaFisicaEntity;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisicaEntity, Long>{
		
}