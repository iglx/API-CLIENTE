package net.atos.api.cliente.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import net.atos.api.cliente.config.PageableBinding;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.domain.TipoPessoaEnum;
import net.atos.api.cliente.factory.PessoaFactory;
import net.atos.api.cliente.repository.PessoaRepository;
import net.atos.api.cliente.repository.entity.PessoaEntity;
import net.atos.api.cliente.service.BuscaPessoaService;
import net.atos.api.cliente.service.CriaPessoa;
import net.atos.api.cliente.service.DeletarPessoaService;
import net.atos.api.cliente.service.InativarPessoaService;

@RestController
@RequestMapping("/v1/cliente")
@Tag(name = "Cliente")
public class PessoaController {
	
	private List<CriaPessoa> criacaoPessoaStrategies;
	
	private BuscaPessoaService buscaPessoaService;
	
	private InativarPessoaService inativarPessoaService;
	
	private DeletarPessoaService deletarPessoaService;
	
	public PessoaController(List<CriaPessoa> strategies,BuscaPessoaService buscaPessoaService, InativarPessoaService inativarPessoaService,DeletarPessoaService deletarPessoaService) {
		super();
		this.criacaoPessoaStrategies = strategies; 
		this.buscaPessoaService = buscaPessoaService;
		this.inativarPessoaService = inativarPessoaService;
		this.deletarPessoaService = deletarPessoaService;
		
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um cliente")
	public ResponseEntity<PessoaVO> criaPessoa(@Valid @RequestBody PessoaVO pessoaVO) {
		
		CriaPessoa criaPessoa = criacaoPessoaStrategies.stream()
				.filter(item -> item.isType(pessoaVO.getTipoPessoaEnum())).findFirst()
				.orElseThrow(() -> new BadRequestException("Tipo Pessoa, n??o Existe."));
		
		PessoaVO pessoaCreated = criaPessoa.persistir(pessoaVO);
		
		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(pessoaCreated.getId()).toUri();

		return ResponseEntity.created(uri).body(pessoaCreated);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta um cliente por id")
	public ResponseEntity<PessoaVO> getClientePorId(@PathVariable("id") Long id) {

		PessoaVO pessoaEncontrada = buscaPessoaService.pessoaVOporId(id);

		return ResponseEntity.ok(pessoaEncontrada);
	}
	
	@PageableBinding
	@GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Buscar por todos os clientes")
	public ResponseEntity<Page<PessoaVO>> buscarTodos(
			@ParameterObject @PageableDefault(sort = {
					"dataCadastro" }, direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

		Page<PessoaVO> pessoaEncontrada = this.buscaPessoaService.porTodos(pageable);

		return ResponseEntity.ok(pessoaEncontrada);
	}

	@PatchMapping(value = "/{id}/desativar", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Inativa o cliente")
	public ResponseEntity<Long> desativaPessoaPorId(@PathVariable("id") Long id) {

		this.inativarPessoaService.inativar(id);

		return ResponseEntity.ok(id);
	}

	@PageableBinding
	@GetMapping(value = "/emissao-periodos/{dataInicio}/{dataFim}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta cliente por per??odo")
	public ResponseEntity<Page<PessoaVO>> getPessoaPorPeriodo(
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim,
			@ParameterObject @PageableDefault(sort = {
					"dataCadastro" }, direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

		Page<PessoaVO> pessoaEncontrada = this.buscaPessoaService.porPeriodoDataCadastro(dataInicio,
				dataFim, pageable);

		return ResponseEntity.ok(pessoaEncontrada);
	}
	
	  @DeleteMapping(value = "/deletar/{id}")
	  @Operation(description = "Delete cliente por id")
	    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
	    {

			this.deletarPessoaService.deletar(id);

			return ResponseEntity.ok(id);
			
	    }

}
