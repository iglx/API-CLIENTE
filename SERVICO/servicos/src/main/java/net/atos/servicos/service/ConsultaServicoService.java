package net.atos.servicos.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.factory.ServicoFactory;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@Service
public class ConsultaServicoService {

	private ServicoRepository servicoRepository;

	public ConsultaServicoService(ServicoRepository pServicoRepository) {
		this.servicoRepository = pServicoRepository;
	}

	public Page<ServicoVO> consultaServico(@NotNull LocalDate dataInicial, @NotNull LocalDate dataFim, Pageable pageable) {

		Page<ServicoEntity> servicosEncontrados = servicoRepository.findByDataEmissaoBetween(dataInicial, dataFim, pageable);

		if (servicosEncontrados.isEmpty()) {
			throw new NotFoundException("Nenhum serviço encontrado nesse periodo.");
		}

		return new PageImpl<>(
				servicosEncontrados.stream().map(ServicoFactory::new).map(ServicoFactory::toVO)
						.collect(Collectors.toList()),
				servicosEncontrados.getPageable(), servicosEncontrados.getTotalElements());

	}

}
