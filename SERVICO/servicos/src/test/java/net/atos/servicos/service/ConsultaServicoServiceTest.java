package net.atos.servicos.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.then;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.repository.ServicoRepository;
import net.atos.servicos.repository.entity.ServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ConsultaServicoServiceTest {

	private ConsultaServicoService servicoService;

	private ServicoRepository servicoRepository;

	private Pageable pageable;

	@BeforeEach
	public void iniciaCadaTeste() {
		this.servicoRepository = Mockito.mock(ServicoRepository.class);
		this.pageable = Mockito.mock(Pageable.class);
		servicoService = new ConsultaServicoService(this.servicoRepository);
	}

	@Test
	public void test_quandoConcultaServicoSemDados_lancarExcessao() {
		assertNotNull(servicoService);

		Page<ServicoEntity> pageServicoVazio = Mockito.mock(Page.class);

		when(pageServicoVazio.isEmpty()).thenReturn(Boolean.TRUE);

		when(this.servicoRepository.findByDataEmissaoBetween(any(), any(), any())).thenReturn(pageServicoVazio);

		var exception = assertThrows(NotFoundException.class,
				() -> servicoService.consultaServico(LocalDate.now(), LocalDate.now(), this.pageable));

		assertNotNull(exception);

	}

	@Test
	public void test_quandoConcultaServicoComDados_retornaVOs() {
		assertNotNull(servicoService);

		Stream<ServicoEntity> entitis = Stream.of(new ServicoEntity(), new ServicoEntity());
		Page<ServicoEntity> pageServicoComDados = Mockito.mock(Page.class);
		Pageable pageableMock = Mockito.mock(Pageable.class);

		when(pageServicoComDados.stream()).thenReturn(entitis);

		when(pageServicoComDados.getPageable()).thenReturn(pageableMock);

		when(this.servicoRepository.findByDataEmissaoBetween(any(), any(), any())).thenReturn(pageServicoComDados);

		Page<ServicoVO> servicosEncontrados = servicoService.consultaServico(LocalDate.now(), LocalDate.now(),
				this.pageable);

		assertNotNull(servicosEncontrados);
		assertEquals(2, servicosEncontrados.getContent().size());
		
		then(this.servicoRepository).should(times(1)).findByDataEmissaoBetween(any(), any(), any());

	}

}
