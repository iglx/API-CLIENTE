package net.atos.api.cliente.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.cliente.domain.ContatoVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoContatoEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;
import net.atos.api.cliente.repository.entity.PessoaFisicaEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PessoaFisicaFactoryTest {

	@Test
	@DisplayName("Testa o factory de VO para entity")
	public void test_quandoCriarEntityPeloVO_sohTransformaparaEntity() {
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(LocalDate.now().minusDays(1l));
		pessoaVO.setDataAlteracao(LocalDateTime.now());
		pessoaVO.setStatusPessoaEnum(StatusPessoaEnum.INATIVO);
		pessoaVO.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoaVO.setNome("Nome do cliente");
		pessoaVO.setEmail("teste@email.com.br");
		pessoaVO.setNascimento("01/01/1901");
		pessoaVO.setNrCpf("999.999.999-00");

		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Brasil, 1500");
		endereco.setBairro("Vila Brasil");
		endereco.setCidade("Java");
		endereco.setEstado("Brasil");
		endereco.setCep("01234-567");
		endereco.setComplemento("Clube dos Javeiros");
		pessoaVO.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO(); 
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(99) 9 9999-9999");
		pessoaVO.addContato(contato);	

		PessoaFisicaEntity entityCriada = 
				new PessoaFisicaFactory(pessoaVO).toEntity();
		
		assertNotNull(entityCriada);
		assertNotNull(entityCriada.getDataCadastro());
		assertEquals(pessoaVO.getDataCadastro(),entityCriada.getDataCadastro());
		
		assertNotNull(entityCriada.getDataAlteracao());
		assertEquals(pessoaVO.getDataAlteracao(),entityCriada.getDataAlteracao());
		
		assertNotNull(entityCriada.getStatusPessoaEnum());
		assertEquals(pessoaVO.getStatusPessoaEnum(),entityCriada.getStatusPessoaEnum());
		
		assertNotNull(entityCriada.getTipoPessoaEnum());
		assertEquals(pessoaVO.getTipoPessoaEnum(),entityCriada.getTipoPessoaEnum());

		assertNotNull(entityCriada.getNome());
		assertEquals(pessoaVO.getNome(),entityCriada.getNome());
		
		assertNotNull(entityCriada.getEmail());
		assertEquals(pessoaVO.getEmail(),entityCriada.getEmail());
		
		assertNotNull(entityCriada.getNascimento());
		assertEquals(pessoaVO.getNascimento(),entityCriada.getNascimento());
		
		assertNotNull(entityCriada.getNrCpf());
		assertEquals(pessoaVO.getNrCpf(),entityCriada.getNrCpf());
		
		assertNotNull(entityCriada.getEndereco());
		assertEquals(pessoaVO.getEndereco().getBairro(),entityCriada.getEndereco().getBairro());
		assertEquals(pessoaVO.getEndereco().getCep(),entityCriada.getEndereco().getCep());
		assertEquals(pessoaVO.getEndereco().getCidade(),entityCriada.getEndereco().getCidade());
		assertEquals(pessoaVO.getEndereco().getComplemento(),entityCriada.getEndereco().getComplemento());
		assertEquals(pessoaVO.getEndereco().getEstado(),entityCriada.getEndereco().getEstado());
		assertEquals(pessoaVO.getEndereco().getLogradouro(),entityCriada.getEndereco().getLogradouro());
		
		assertNotNull(entityCriada.getContatos());
		assertEquals(pessoaVO.getContatos().size(),entityCriada.getContatos().size());
		assertEquals(pessoaVO.getContatos().get(0).getTipoContato(),entityCriada.getContatos().get(0).getTipoContato());
		assertEquals(pessoaVO.getContatos().get(0).getNumero(),entityCriada.getContatos().get(0).getNumero());
		
		PessoaVO voCriado = 
				new PessoaFisicaFactory(entityCriada).toVO();

		assertNotNull(voCriado);
		assertNotNull(voCriado.getDataCadastro());
		assertEquals(pessoaVO.getDataCadastro(),voCriado.getDataCadastro());
		
		assertNotNull(voCriado.getDataAlteracao());
		assertEquals(pessoaVO.getDataAlteracao(),voCriado.getDataAlteracao());
		
		assertNotNull(voCriado.getStatusPessoaEnum());
		assertEquals(pessoaVO.getStatusPessoaEnum(),voCriado.getStatusPessoaEnum());
		
		assertNotNull(voCriado.getTipoPessoaEnum());
		assertEquals(pessoaVO.getTipoPessoaEnum(),voCriado.getTipoPessoaEnum());

		assertNotNull(voCriado.getNome());
		assertEquals(pessoaVO.getNome(),voCriado.getNome());
		
		assertNotNull(voCriado.getEmail());
		assertEquals(pessoaVO.getEmail(),voCriado.getEmail());
		
		assertNotNull(voCriado.getNascimento());
		assertEquals(pessoaVO.getNascimento(),voCriado.getNascimento());
		
		assertNotNull(voCriado.getNrCpf());
		assertEquals(pessoaVO.getNrCpf(),voCriado.getNrCpf());
		
		assertNotNull(voCriado.getEndereco());
		assertEquals(pessoaVO.getEndereco().getBairro(),voCriado.getEndereco().getBairro());
		assertEquals(pessoaVO.getEndereco().getCep(),voCriado.getEndereco().getCep());
		assertEquals(pessoaVO.getEndereco().getCidade(),voCriado.getEndereco().getCidade());
		assertEquals(pessoaVO.getEndereco().getComplemento(),voCriado.getEndereco().getComplemento());
		assertEquals(pessoaVO.getEndereco().getEstado(),voCriado.getEndereco().getEstado());
		assertEquals(pessoaVO.getEndereco().getLogradouro(),voCriado.getEndereco().getLogradouro());
		
		assertNotNull(voCriado.getContatos());
		assertEquals(pessoaVO.getContatos().size(),voCriado.getContatos().size());
		assertEquals(pessoaVO.getContatos().get(0).getTipoContato(),voCriado.getContatos().get(0).getTipoContato());
		assertEquals(pessoaVO.getContatos().get(0).getNumero(),voCriado.getContatos().get(0).getNumero());
		
	}

}
