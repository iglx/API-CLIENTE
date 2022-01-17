package br.com.bancointer.corretora.controllers;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bancointer.corretora.dto.EmpresaDTO;
import br.com.bancointer.corretora.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "v1/empresas")
@Tag(name = "Empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Listar todas as empresas")
	public ResponseEntity<List<EmpresaDTO>> findAll() {
		List<EmpresaDTO> list = empresaService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/ativas", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Listar empresas ativas")
	public ResponseEntity<List<EmpresaDTO>> empresasAtivas() {
		List<EmpresaDTO> list = empresaService.empresasAtivas();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/inativas", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Listar empresas inativas")
	public ResponseEntity<List<EmpresaDTO>> empresasInativas() {
		List<EmpresaDTO> list = empresaService.empresasInativas();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria uma empresa")
	public ResponseEntity<EmpresaDTO> insert(@Valid @RequestBody EmpresaDTO empresaDTO) {
		empresaDTO = empresaService.insert(empresaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresaDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(empresaDTO);
	}

	@PutMapping("/{id}/inativar")
	@Operation(description = "Inativa empresa")
	public ResponseEntity<EmpresaDTO> inativaEmpresa(@PathVariable Long id) {
		EmpresaDTO dto = empresaService.inativarEmpresa(id);
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping("/{id}/ativar")
	@Operation(description = "Ativa empresa")
	public ResponseEntity<EmpresaDTO> ativaEmpresa(@PathVariable Long id) {
		EmpresaDTO dto = empresaService.ativarEmpresa(id);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/deletar/{id}")
	@Operation(description = "Delete cliente por id")
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {

		this.empresaService.deletar(id);

		return ResponseEntity.ok(id);

	}

	@PutMapping("/{id}/valor")
	@Operation(description = "Atualizar Preço")
	public ResponseEntity<EmpresaDTO> atualizarPreço(@PathVariable Long id, @Valid @RequestBody Double valor) {
		EmpresaDTO dto = empresaService.atualizar(id, valor);
		if (Objects.nonNull(dto)) {
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}

}
