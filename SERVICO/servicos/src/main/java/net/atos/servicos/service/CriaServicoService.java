package net.atos.servicos.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.factory.ServicoFactory;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@Service
public class CriaServicoService {

	private Validator validator;

	private ServicoRepository servicoRepository;

	public CriaServicoService(Validator pValidator, ServicoRepository pServicoRepository) {
		this.validator = pValidator;
		this.servicoRepository = pServicoRepository;
	}

	public ServicoVO criarServico(ServicoVO servicoVO) {
		Set<ConstraintViolation<ServicoVO>> validateMessages = this.validator.validate(servicoVO);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Serviço inválido", validateMessages);
		}

		ServicoEntity entity = new ServicoFactory(servicoVO).toEntity();

		entity = this.servicoRepository.save(entity);

		servicoVO.setId(entity.getId());

		return servicoVO;
	}

}
