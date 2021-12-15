package net.atos.cliente.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.atos.cliente.config.PageableBinding;
import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.TipoPessoaEnum;
import net.atos.cliente.repository.ClienteRepository;
import net.atos.cliente.service.BuscaClienteService;
import net.atos.cliente.service.CriaPessoa;
import net.atos.cliente.service.InativarClienteService;

@RestController
@RequestMapping("/v1/cliente")
@Tag(name = "Cliente")
public class ClienteController {
	
	private List<CriaPessoa> criacaoClienteStrategies;
	
	private BuscaClienteService buscaClienteService;
	
	private InativarClienteService inativarClienteService;
	
	public ClienteController(List<CriaPessoa> strategies,BuscaClienteService buscaNotaFiscalService, InativarClienteService inativarClienteService,ClienteRepository clienteRepository) {
		super();
		this.criacaoClienteStrategies = strategies; 
		this.buscaClienteService = buscaNotaFiscalService;
		this.inativarClienteService = inativarClienteService;
		
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um cliente")
	public ResponseEntity<PessoaVO> criaNotaFiscal(@Valid @RequestBody PessoaVO pessoaVO) {
		
		CriaPessoa criaCliente = criacaoClienteStrategies.stream()
				.filter(item -> item.isType(pessoaVO.getTipoPessoaEnum())).findFirst()
				.orElseThrow(() -> new BadRequestException("Tipo Pessoa, não Existe."));
		
		PessoaVO pessoaCreated = criaCliente.persistir(pessoaVO);
		
		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(pessoaCreated.getId()).toUri();

		return ResponseEntity.created(uri).body(pessoaCreated);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta um cliente por id")
	public ResponseEntity<PessoaVO> getClientePorId(@PathVariable("id") Long id) {

		PessoaVO clienteEncontrado = buscaClienteService.pessoaVOporId(id);

		return ResponseEntity.ok(clienteEncontrado);
	}

	@PatchMapping(value = "/{id}/desativar", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Inativa o cliente")
	public ResponseEntity<Long> desativaClientePorId(@PathVariable("id") Long id) {

		this.inativarClienteService.inativar(id);

		return ResponseEntity.ok(id);
	}

	@PageableBinding
	@GetMapping(value = "/emissao-periodos/{dataInicio}/{dataFim}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta cliente por período")
	public ResponseEntity<Page<PessoaVO>> getNotaFiscaisPorPeriodo(
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim,
			@ParameterObject @PageableDefault(sort = {
					"dataCadastro" }, direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

		Page<PessoaVO> notasFiscaisEncontradas = this.buscaClienteService.porPeriodoDataCadastro(dataInicio,
				dataFim, pageable);

		return ResponseEntity.ok(notasFiscaisEncontradas);

	}
	
//	  @DeleteMapping(value = "/cliente/{id}")
//	  @Operation(description = "Delete cliente por id")
//	    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
//	    {
//		  
//			PessoaVO clienteEncontrado = buscaClienteService.porId(id);
//			
//	        PessoaVO cliente = buscaClienteService.porId(id);
//	        if(cliente.getId().equals(id)){
//	        	criacaoClienteStrategies.   deleteById(cliente.getId());
//	            return new ResponseEntity<>(HttpStatus.OK);
//	        }
//	        else
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }

}
