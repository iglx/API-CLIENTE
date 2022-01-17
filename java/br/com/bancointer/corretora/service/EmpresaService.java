package br.com.bancointer.corretora.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bancointer.corretora.dto.EmpresaDTO;
import br.com.bancointer.corretora.dto.StatusEmpresaEnum;
import br.com.bancointer.corretora.entity.Empresa;
import br.com.bancointer.corretora.repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;

	@Transactional(readOnly = true)
	public List<EmpresaDTO> findAll() {
		List<Empresa> list = (List<Empresa>) repository.findAll();
		return list.stream().map(x -> new EmpresaDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public EmpresaDTO insert(EmpresaDTO dto) {
		Empresa empresa = new Empresa(null, dto.getNome(), dto.getTicker(), dto.getValor(), dto.getStatus());
		empresa = repository.save(empresa);
		return new EmpresaDTO(empresa);
	}

	@Transactional
	public EmpresaDTO inativarEmpresa(Long id) {
		Empresa empresa = repository.getById(id);
		if (empresa.getStatus().equals(StatusEmpresaEnum.INATIVA)) {
			throw new BadRequestException("A empresa já está inativa.");
		}
		empresa.setStatus(StatusEmpresaEnum.INATIVA);
		empresa = repository.save(empresa);
		return new EmpresaDTO(empresa);
	}

	@Transactional
	public void deletar(Long id) {

		Empresa empresaEncontrada = repository.getById(id);

		if (empresaEncontrada.equals(null)) {
			throw new BadRequestException("Não existe empresa  com o id " + id);
		}

		repository.deleteById(empresaEncontrada.getId());

	}

	@Transactional
	public EmpresaDTO ativarEmpresa(Long id) {
		Empresa empresa = repository.getById(id);
		if (empresa.getStatus().equals(StatusEmpresaEnum.ATIVA)) {
			throw new BadRequestException("A empresa já está ativa.");
		}
		empresa.setStatus(StatusEmpresaEnum.ATIVA);
		empresa = repository.save(empresa);
		return new EmpresaDTO(empresa);
	}

	@Transactional
	public EmpresaDTO atualizar(Long id, Double valor) {

		if (valor.isNaN()) {
			throw new BadRequestException("Digite um valor válido.");
		}
		Empresa empresaEncontrada = repository.getById(id);

		if (Objects.nonNull(empresaEncontrada)) {
			empresaEncontrada.setValor(valor);
			empresaEncontrada = repository.saveAndFlush(empresaEncontrada);
			return new EmpresaDTO(empresaEncontrada);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<EmpresaDTO> empresasAtivas() {
		List<Empresa> list = repository.findByStatus(StatusEmpresaEnum.ATIVA);
		return list.stream().map(x -> new EmpresaDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<EmpresaDTO> empresasInativas() {
		List<Empresa> list = repository.findByStatus(StatusEmpresaEnum.INATIVA);
		return list.stream().map(x -> new EmpresaDTO(x)).collect(Collectors.toList());
	}

}
