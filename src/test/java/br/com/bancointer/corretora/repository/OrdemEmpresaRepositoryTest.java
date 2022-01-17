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


@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // <- ativa testes usando o próprio bd
public class OrdemEmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    public void whenCreate_thenPersistData () {

        Empresa empresa = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
        this.empresaRepository.save(empresa);

        assertThat(empresa.getId()).isNotNull();
        assertThat(empresa.getNome()).isEqualTo("Magazine Luiza");
        assertThat(empresa.getTicker()).isEqualTo("MGZ1");
        assertThat(empresa.getValor()).isEqualTo(40.00);
        assertThat(empresa.getStatus()).isEqualTo(StatusEmpresaEnum.ATIVA);

    }

    @Test
    public void whenDelete_thenRemoveData () {
    	Empresa empresa = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);
        this.empresaRepository.save(empresa);
        empresaRepository.delete(empresa);
        assertThat(empresaRepository.findById(empresa.getId())).isEmpty();

    }

    @Test
    public void whenUpdate_thenChangeAndPersistData () {
    	Empresa empresa = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);
        this.empresaRepository.save(empresa);

        empresa.setNome("Inter Digital");
        empresa.setTicker("DIG8");
        empresa.setValor(30.00);
        this.empresaRepository.saveAndFlush(empresa);

        empresa = empresaRepository.findById(empresa.getId()).orElse(null);

        assertThat(empresa.getNome()).isEqualTo("Inter Digital");
        assertThat(empresa.getTicker()).isEqualTo("DIG8");
        assertThat(empresa.getValor()).isEqualTo(30.00);

    }

    @Test
    public void whenfindByStatus () {
		Empresa empresa1 = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa2 = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);

        this.empresaRepository.save(empresa1);
        this.empresaRepository.save(empresa2);

        List<Empresa> empresas = empresaRepository.findByStatus(StatusEmpresaEnum.ATIVA);

        assertThat(empresas.size()).isEqualTo(2);

    }

    @Test
    public void whenfindByEmpresas () {
		Empresa empresa1 = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa2 = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa3 = new Empresa(null, "Samsung", "SMG4", 10.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa4 = new Empresa(null, "Google", "GOL3", 12.00, StatusEmpresaEnum.ATIVA);

        this.empresaRepository.save(empresa1);
        this.empresaRepository.save(empresa2);
        this.empresaRepository.save(empresa3);
        this.empresaRepository.save(empresa4);

        List<Empresa> empresas = empresaRepository.findByEmpresas(20.00);

        assertThat(empresas.size()).isEqualTo(3);

    }
    
    @Test
    public void whenfindAll () {
		Empresa empresa1 = new Empresa(null, "Magazine Luiza", "MGZ1", 40.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa2 = new Empresa(null, "Inter", "INT2", 15.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa3 = new Empresa(null, "Samsung", "SMG4", 10.00, StatusEmpresaEnum.ATIVA);
		Empresa empresa4 = new Empresa(null, "Google", "GOL3", 12.00, StatusEmpresaEnum.ATIVA);

        this.empresaRepository.save(empresa1);
        this.empresaRepository.save(empresa2);
        this.empresaRepository.save(empresa3);
        this.empresaRepository.save(empresa4);

        List<Empresa> empresas = empresaRepository.findAll();

        assertThat(empresas.size()).isEqualTo(4);

    }
    
}