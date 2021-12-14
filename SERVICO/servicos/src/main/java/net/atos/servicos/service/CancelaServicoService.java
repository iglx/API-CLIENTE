package net.atos.servicos.service;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.servicos.domain.CancelaServicoVO;
import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.factory.ServicoFactory;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@Service
public class CancelaServicoService {

private Validator validator;
	
	private ServicoRepository servicoRepository;

	public CancelaServicoService(Validator pValidator, ServicoRepository pServicoRepository) {
		this.validator = pValidator;
		this.servicoRepository = pServicoRepository;
	}
	
	@Transactional
	public ServicoVO cancelaServico(CancelaServicoVO cancelaServicoVO) {
		Set<ConstraintViolation<CancelaServicoVO>> validateMessages = 
				this.validator.validate(cancelaServicoVO);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Serviço inválido", validateMessages);
		}
		
		ServicoEntity servicoEncontrado = servicoRepository.findByIdCliente(cancelaServicoVO.getIdCliente())
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado, id do cliente: " 
						+ cancelaServicoVO.getIdCliente()));
		
		servicoEncontrado.setCancelado(Boolean.TRUE);
		
		ServicoVO vo = new ServicoFactory(servicoEncontrado).toVO();
		
		return vo;
	
	}

	
}
