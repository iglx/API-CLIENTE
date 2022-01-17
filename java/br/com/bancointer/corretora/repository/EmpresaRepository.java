package br.com.bancointer.corretora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bancointer.corretora.dto.StatusEmpresaEnum;
import br.com.bancointer.corretora.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@Query("select u from Empresa u where u.status = :param")
	List<Empresa> findByStatus(@Param("param") StatusEmpresaEnum param);

	@Query("select u from Empresa u where u.status = 'ATIVA' and u.valor <= :valor")
	List<Empresa> findByEmpresas(@Param("valor") double valor);

}