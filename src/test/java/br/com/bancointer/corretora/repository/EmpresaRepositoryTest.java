package br.com.bancointer.corretora.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.bancointer.corretora.dto.StatusEmpresaEnum;
import br.com.bancointer.corretora.entity.Empresa;
import br.com.bancointer.corretora.entity.Ordem;
import br.com.bancointer.corretora.entity.OrdemEmpresa;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // <- ativa testes usando o próprio bd
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private OrdemEmpresaRepository ordemEmpresaRepository;

	@Autowired
	private OrdemRepository ordemRepository;

	@Test
	public void whenCreate_thenPersistData() {

		Empresa empresa = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
		this.empresaRepository.save(empresa);

		Ordem ordem = new Ordem(null, "752.644.080-17", 400.00, 1l, null);
		this.ordemRepository.save(ordem);

		OrdemEmpresa ordemEmpresa = new OrdemEmpresa(null, 1l, 400.00, ordem, empresa);
		this.ordemEmpresaRepository.save(ordemEmpresa);

		assertThat(ordemEmpresa.getId()).isNotNull();
		assertThat(ordemEmpresa.getQuantidade()).isEqualTo(2l);
		assertThat(ordemEmpresa.getValorTotal()).isEqualTo(400.00);
		assertThat(ordemEmpresa.getOrdem()).isEqualTo(ordem);
		assertThat(ordemEmpresa.getEmpresa()).isEqualTo(empresa);

	}

	@Test
	public void whenfindAll() {

		Empresa empresa = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa2 = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);
		this.empresaRepository.save(empresa);
		this.empresaRepository.save(empresa2);

		Ordem ordem = new Ordem(null, "554.185.550-06", 400.00, 1l, null);
		Ordem ordem2 = new Ordem(null, "752.644.080-17", 100.00, 1l, null);
		this.ordemRepository.save(ordem);
		this.ordemRepository.save(ordem2);

		OrdemEmpresa ordemEmpresa = new OrdemEmpresa(null, 2l, 400.00, ordem, empresa);
		OrdemEmpresa ordemEmpresa2 = new OrdemEmpresa(null, 2l, 400.00, ordem2, empresa2);
		this.ordemEmpresaRepository.save(ordemEmpresa);
		this.ordemEmpresaRepository.save(ordemEmpresa2);

		List<OrdemEmpresa> empresas = ordemEmpresaRepository.findAll();

		assertThat(empresas.size()).isEqualTo(2);

	}

}