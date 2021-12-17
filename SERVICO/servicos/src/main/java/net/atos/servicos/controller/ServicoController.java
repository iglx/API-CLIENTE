package net.atos.servicos.controller;

import java.time.LocalDate;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.service.ConsultaServicoService;

@RestController
@RequestMapping("api/servicos")
public class ServicoController {

	private ConsultaServicoService consultaServicoService;
	
	public ServicoController(ConsultaServicoService pConsultaServicoService) {
		this.consultaServicoService = pConsultaServicoService;
	}
	
	@GetMapping("/inicial/{inicial}/final/{fim}")
	public ResponseEntity<Page<ServicoVO>> buscaServico(
			@PathVariable("inicial") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicial,
			@PathVariable("fim") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim,
			@ParameterObject @PageableDefault(sort = {"dataEmissao"}, direction = Direction.DESC, page = 0, size = 10) 
			Pageable pageable) {
	
		Page<ServicoVO> serviscosConsultados = consultaServicoService.consultaServico(dataInicial, dataFim, pageable);
		
		return ResponseEntity.ok(serviscosConsultados);
	}
	
}
