package br.com.bancointer.corretora.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bancointer.corretora.dto.EmpresaDTO;
import br.com.bancointer.corretora.dto.OrdemDTO;
import br.com.bancointer.corretora.entity.Empresa;
import br.com.bancointer.corretora.entity.Ordem;
import br.com.bancointer.corretora.repository.EmpresaRepository;
import br.com.bancointer.corretora.repository.OrdemRepository;

@Service
public class OrdemService {

	@Autowired
	private EmpresaRepository repositoryEmpresa;

	@Autowired
	private OrdemRepository repositoryOrdem;

	@Transactional(readOnly = true)
	public List<EmpresaDTO> findAll() {
		List<Empresa> list = (List<Empresa>) repositoryEmpresa.findAll();
		return list.stream().map(x -> new EmpresaDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public OrdemDTO insert(OrdemDTO dto) {
		Ordem ordem = new Ordem(null, dto.getCpf(), dto.getValorInvestimento(), dto.getNumeroEmpresa(), null);
		ordem = repositoryOrdem.save(ordem);
		return new OrdemDTO(ordem);
	}

	@Transactional
	public void deletar(Long id) {

		Empresa empresaEncontrada = repositoryEmpresa.getById(id);

		if (empresaEncontrada.equals(null)) {
			throw new BadRequestException("Não existe empresa  com o id " + id);
		}

		repositoryEmpresa.deleteById(empresaEncontrada.getId());

	}

}
