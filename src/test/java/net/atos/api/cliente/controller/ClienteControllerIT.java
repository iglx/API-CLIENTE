package net.atos.api.cliente.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import net.atos.api.cliente.controller.page.*;


import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.cliente.domain.ContatoVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.StatusPessoaEnum;
import net.atos.api.cliente.domain.TipoContatoEnum;
import net.atos.api.cliente.domain.TipoPessoaEnum;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class ClienteControllerIT {

	private static final String URI_CLIENTE = "/v1/cliente";
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private ObjectMapper mapper;
	
    private MockMvc mockMvc;
    
    @Autowired
    private EntityManager entityManager;
    
    @BeforeAll
    public void setup() {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        assertNotNull(this.entityManager);
        

    }
    
    
    @Test
    @DisplayName("Envio de pessoa sem os campos obrigatórios")
    public void test_envioCamposSemDados_retorna400() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
    	
    	this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isBadRequest());
    	
    }

    
    @Test    
    @DisplayName("Cria Pessoa Fisica")
    public void test_criaPessoaFisica_retornoCriado() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf("111111111");
		pessoa.setNrCnpj(null);
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Teste");
		endereco.setBairro("Torre");
		endereco.setCidade("Recife");
		endereco.setCep("5000000");
		endereco.setEstado("PE");
		endereco.setComplemento("1 Andar");
		
		pessoa.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO();
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(81) 9999999");
		pessoa.addContato(contato);
		
		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
		contato.setNumero("(81) 3333333");
		pessoa.addContato(contato);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	PessoaVO pessoaCriada = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						PessoaVO.class);
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_CLIENTE.concat("/{id}"),
    					pessoaCriada.getId()))
    					.andDo(print())
    					.andExpect(status().isOk());
    	
    	PessoaVO clienteConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				PessoaVO.class);
    	
    	assertEquals(2, clienteConsultado.getContatos().size());
    	assertEquals(TipoPessoaEnum.FISICA, clienteConsultado.getTipoPessoaEnum());
    }
    
    @Test    
    @DisplayName("Cria Pessoa Juridica")
    public void test_criaPessoaJuridica_retornoCriado() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.JURIDICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf(null);
		pessoa.setNrCnpj("11111");
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Teste");
		endereco.setBairro("Torre");
		endereco.setCidade("Recife");
		endereco.setCep("5000000");
		endereco.setEstado("PE");
		endereco.setComplemento("1 Andar");
		
		pessoa.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO();
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(81) 9999999");
		pessoa.addContato(contato);
		
		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
		contato.setNumero("(81) 3333333");
		pessoa.addContato(contato);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	PessoaVO pessoaCriada = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						PessoaVO.class);
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_CLIENTE.concat("/{id}"),
    					pessoaCriada.getId()))
    					.andDo(print())
    					.andExpect(status().isOk());
    	
    	PessoaVO clienteConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				PessoaVO.class);
    	
    	assertEquals(2, clienteConsultado.getContatos().size());
    	assertEquals(TipoPessoaEnum.JURIDICA, clienteConsultado.getTipoPessoaEnum());
    }
    
    
    
    @Test    
    @DisplayName("Cria cliente sem contato")
    public void test_criaPessoaSemContato() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf("111111111");
		pessoa.setNrCnpj(null);
		
		ContatoVO contato = new ContatoVO();
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(81) 9999999");
		pessoa.addContato(contato);
		
		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
		contato.setNumero("(81) 3333333");
		pessoa.addContato(contato);
    	
		ResultActions andExpect = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
    			.andExpect(status().isBadRequest());
		
		
		System.out.println(andExpect.andReturn().getRequest());
    	
    }
    
    
    @Test    
    @DisplayName("Cria cliente sem endereco")
    public void test_criaPessoaSemEndereco() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf("111111111");
		pessoa.setNrCnpj(null);
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Teste");
		endereco.setBairro("Torre");
		endereco.setCidade("Recife");
		endereco.setCep("5000000");
		endereco.setEstado("PE");
		endereco.setComplemento("1 Andar");
		
		pessoa.setEndereco(endereco);
    	

		ResultActions andExpect = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
    			.andExpect(status().isBadRequest());
		
		
		System.out.println(andExpect.andReturn().getRequest());
    	
    }
    
    @Test    
    @DisplayName("Tenta Criar cliente com tipo operacao inexistente")
    public void test_criaclientecomTipoOperacaoIne_retornoBadRequest() throws Exception {
    	String pessoaString = 
    			"{\"datacadastro\":\"2021-12-16\",\"dataalteracao\":\"2021-12-16t22:16:54.435z\",\"statuspessoaenum\":\"ATIVO\","
    			+ "\"tipopessoaenum\":\"MISTERIOSA\",\"nome\":\"pessoa5\",\"email\":\"string\",\"nascimento\":\"string\",\"nrcpf\":\"22222222\","
    			+ "\"nrcnpj\":\"11111111111\",\"endereco\":{\"logradouro\":\"string\",\"bairro\":\"string\",\"cidade\":\"string\","
    			+ "\"estado\":\"string\",\"cep\":\"string\",\"complemento\":\"string\"},\"contatos\":[{\"tipocontato\":\"CELULAR\",\"numero\":\"string\"}]}";
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(pessoaString)
    			).andDo(print())
    			.andExpect(status().isBadRequest());

    }
   
    
    @Test    
    @DisplayName("Cria pessoa e desativa")
    public void test_criaPessoaEDesativa_retornoCriado() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf("111111111");
		pessoa.setNrCnpj(null);
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Teste");
		endereco.setBairro("Torre");
		endereco.setCidade("Recife");
		endereco.setCep("5000000");
		endereco.setEstado("PE");
		endereco.setComplemento("1 Andar");
		
		pessoa.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO();
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(81) 9999999");
		pessoa.addContato(contato);
		
		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
		contato.setNumero("(81) 3333333");
		pessoa.addContato(contato);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	PessoaVO pessoaCriada = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						PessoaVO.class);
    	
    	this.mockMvc.perform(
    			MockMvcRequestBuilders.patch(URI_CLIENTE.concat("/{id}/desativar"),
    					pessoaCriada.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isOk());
    }
    
    
    
    @Test    
    @DisplayName("Cria pessoa e deleta")
    public void test_criaPessoaEDeleta_retornoCriado() throws Exception {
    	PessoaVO pessoa =  new PessoaVO();
		pessoa.setId(null);
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setDataAlteracao(LocalDateTime.now());
		pessoa.setStatusPessoaEnum(StatusPessoaEnum.ATIVO);
		pessoa.setTipoPessoaEnum(TipoPessoaEnum.FISICA);
		pessoa.setNome("Adolfo teste");
		pessoa.setEmail("adolfo@gmail.com");
		pessoa.setNascimento("20/10/1980");
		pessoa.setNrCpf("111111111");
		pessoa.setNrCnpj(null);
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua do Teste");
		endereco.setBairro("Torre");
		endereco.setCidade("Recife");
		endereco.setCep("5000000");
		endereco.setEstado("PE");
		endereco.setComplemento("1 Andar");
		
		pessoa.setEndereco(endereco);
		
		ContatoVO contato = new ContatoVO();
		contato.setTipoContato(TipoContatoEnum.CELULAR);
		contato.setNumero("(81) 9999999");
		pessoa.addContato(contato);
		
		contato.setTipoContato(TipoContatoEnum.RESIDENCIAL);
		contato.setNumero("(81) 3333333");
		pessoa.addContato(contato);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	PessoaVO pessoaCriada = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						PessoaVO.class);
    	
    	this.mockMvc.perform(
    			MockMvcRequestBuilders.patch(URI_CLIENTE.concat("/deletar/{id}"),
    					pessoaCriada.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pessoa))
    			).andDo(print())
    			.andExpect(status().isMethodNotAllowed());
    }
    
    
    @Test    
    @DisplayName("Consulta cliente por periodo")
    public void test_criaPessoaPorPeriodo_retornoOK() throws Exception {
    	String dataInicio = LocalDate.now().minusDays(1l).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    	String dataFim = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    	   	
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_CLIENTE.concat("/emissao-periodos/{dataInicio}/{dataFim}"),
    					dataInicio,dataFim))
    					.andDo(print())
    					.andExpect(status().isOk());	
    	
    	
    	PaginatedResponse<PessoaVO> pessoasConsultadas = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				new TypeReference<PaginatedResponse<PessoaVO>>() { });
    	
    	System.out.println("(Consulta por periodo) Quantidade de clientes = "+pessoasConsultadas.getSize());
    	assertNotNull(pessoasConsultadas);
    }
    
    

}
