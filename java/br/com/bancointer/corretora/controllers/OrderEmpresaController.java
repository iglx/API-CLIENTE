package br.com.bancointer.corretora.controllers;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancointer.corretora.dto.InvestimentoDTO;
import br.com.bancointer.corretora.dto.OrdemDTO;
import br.com.bancointer.corretora.dto.OrdemEmpresaDTO;
import br.com.bancointer.corretora.service.OrdemEmpresaService;
import br.com.bancointer.corretora.service.OrdemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "v1/ordens")
@Tag(name = "Ordem")
public class OrderEmpresaController {

	@Autowired
	private OrdemEmpresaService ordemEmpresaService;

	@Autowired
	private OrdemService ordemService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Listar todas as ordens")
	public ResponseEntity<List<OrdemEmpresaDTO>> findAll() {
		List<OrdemEmpresaDTO> list = ordemEmpresaService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria uma ordem")
	public ResponseEntity<List<OrdemEmpresaDTO>> insert(@RequestBody OrdemDTO ordemDTO) {
		ordemDTO = ordemService.insert(ordemDTO);
		ordemDTO.getId(); // 1
		List<OrdemEmpresaDTO> ordemEmpresaDTO = ordemEmpresaService.insert(ordemDTO);
		return ResponseEntity.created(null).body(ordemEmpresaDTO);
	}

	@GetMapping(value = "/investimentos", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Listar todas as ordens de investimento")
	public ResponseEntity<List<InvestimentoDTO>> todosIntestimentos() {
		List<InvestimentoDTO> listaIvestimento = ordemEmpresaService.verInvestimento();
		return ResponseEntity.ok().body(listaIvestimento);
	}

}
