package net.atos.servicos.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.servicos.domain.CancelaServicoVO;
import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CancelaServicoServiceTest {

	private CancelaServicoService cancelaServicoService;

	private ServicoRepository servicoRepository;

	private Validator validator;

	@BeforeAll
	public void iniciaTodos() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

		this.validator = validatorFactory.getValidator();
	}

	@BeforeEach
	public void iniciaCadaTeste() {
		this.servicoRepository = Mockito.mock(ServicoRepository.class);
		cancelaServicoService = new CancelaServicoService(this.validator, this.servicoRepository);
	}

	@Test
	public void test_quandoNaoPassarObjeto_lancarExcessao() {
		assertNotNull(cancelaServicoService);

		CancelaServicoVO cancelaServicoVO = null;
		var exception = assertThrows(IllegalArgumentException.class,
				() -> cancelaServicoService.cancelaServico(cancelaServicoVO));

		assertNotNull(exception);
	}

	@Test
	public void test_quandoNaoPassarAtributosObrigatorios_lancarExcessao() {
		assertNotNull(cancelaServicoService);

		CancelaServicoVO cancelaServicoVO = new CancelaServicoVO();
		var exception = assertThrows(ConstraintViolationException.class,
				() -> cancelaServicoService.cancelaServico(cancelaServicoVO));

		assertNotNull(exception);
		assertEquals(1, exception.getConstraintViolations().size());

		List<String> mensagens = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());

		assertThat(mensagens, hasItems("ID do cliente não pode ser NULL"));

	}

	@Test
	public void test_quandoNaoEncontraServico_lancarExcessao() {
		assertNotNull(cancelaServicoService);

		CancelaServicoVO cancelaServicoVO = new CancelaServicoVO();
		cancelaServicoVO.setIdCliente(1l);

		when(this.servicoRepository.findByIdCliente(anyLong())).thenReturn(Optional.empty());

		var exception = assertThrows(NotFoundException.class,
				() -> cancelaServicoService.cancelaServico(cancelaServicoVO));

		assertNotNull(exception);

		then(this.servicoRepository).should(times(1)).findByIdCliente(anyLong());
	}

	@Test
	public void test_quandoServicoCancelado_alteraEstado() {
		assertNotNull(cancelaServicoService);

		CancelaServicoVO cancelaServicoVO = new CancelaServicoVO();
		cancelaServicoVO.setIdCliente(1l);

		ServicoEntity servicoEncontrado = new ServicoEntity();
		servicoEncontrado.setId(1l);

		when(this.servicoRepository.findByIdCliente(anyLong())).thenReturn(Optional.of(servicoEncontrado));

		ServicoVO servicoCancelado = cancelaServicoService.cancelaServico(cancelaServicoVO);

		then(this.servicoRepository).should(times(1)).findByIdCliente(anyLong());

		assertNotNull(servicoCancelado);
		assertEquals(1l, servicoCancelado.getId());

	}

}
