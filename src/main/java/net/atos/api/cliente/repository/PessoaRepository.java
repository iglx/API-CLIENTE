package net.atos.api.cliente.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.cliente.repository.entity.PessoaEntity;

@Repository
public interface PessoaRepository extends PagingAndSortingRepository<PessoaEntity, Long>{
	
	public Page<PessoaEntity> findByDataCadastroBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
	
}