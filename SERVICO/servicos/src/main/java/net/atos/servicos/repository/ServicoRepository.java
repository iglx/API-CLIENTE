package net.atos.servicos.repository;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.atos.servicos.repository.entity.ServicoEntity;

@Repository
public interface ServicoRepository extends PagingAndSortingRepository<ServicoEntity, Long>{
	
	public Optional<ServicoEntity> findByIdCliente(Long idCliente);

	public Page<ServicoEntity> findByDataEmissaoBetween(@NotNull LocalDate dataInicial, @NotNull LocalDate fim, Pageable pageable);
	
}
