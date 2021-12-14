package net.atos.servicos.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CriaServicoServiceTest {

	private CriaServicoService servicoService;
	
	private ServicoRepository servicoRepository;
	
	private Validator validator;
	
	@BeforeAll
	public void iniciaTodos() {
		ValidatorFactory validatorFactory = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactory.getValidator();
	}

	@BeforeEach
	public void iniciaCadaTeste() {
		this.servicoRepository = Mockito.mock(ServicoRepository.class);
		servicoService = new CriaServicoService(this.validator, 
				this.servicoRepository);
	}
	
	@Test
	public void test_quandoNaoPassarObjeto_lancarExcessao() {
		assertNotNull(servicoService);

		ServicoVO servicoVO = null;
		var exception = assertThrows(IllegalArgumentException.class, 
				() -> servicoService.criarServico(servicoVO));
		
		assertNotNull(exception);
	}

	@Test
	public void test_quandoNaoPassarAtributosObrigatorios_lancarExcessao() {
		assertNotNull(servicoService);

		ServicoVO servicoVO = new ServicoVO();
		var exception = assertThrows(ConstraintViolationException.class, () -> servicoService.criarServico(servicoVO));
		
		assertNotNull(exception);
		assertEquals(4, exception.getConstraintViolations().size());
		
		List<String> mensagens = exception.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("ID do cliente não pode ser NULL", 
				"Valor não pode ser NULL", 
				"Tipo de cliente não pode ser NULL", 
				"Data de emissão não pode ser NULL"));

	}
	
	@Test
	public void test_quandoTentarRegistrarServicoComUniqueKeyViolation_lancarExcessao() {
		assertNotNull(servicoService);

		ServicoVO servicoVO = new ServicoVO();
		servicoVO.setIdCliente(1l);
		servicoVO.setTipoCliente("fisico");
		servicoVO.setValor(BigDecimal.ONE);
		servicoVO.setDataEmissao(LocalDate.now());
		
		ServicoEntity entitySaved = new ServicoEntity();
		entitySaved.setId(1l);
		
		when(this.servicoRepository.save(any())).thenReturn(entitySaved);
		
		ServicoVO servicoCriado = servicoService.criarServico(servicoVO);
		
		then(this.servicoRepository).should(times(1)).save(any());
		
		assertNotNull(entitySaved);
		assertEquals(1l, servicoCriado.getId());
	}
}
