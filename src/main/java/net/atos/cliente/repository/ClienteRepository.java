package net.atos.cliente.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.atos.cliente.repository.entity.PessoaEntity;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<PessoaEntity, Long>{
	
	public Page<PessoaEntity> findByDataCadastroBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
	
}