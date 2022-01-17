package br.com.bancointer.corretora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bancointer.corretora.entity.OrdemEmpresa;

@Repository
public interface OrdemEmpresaRepository extends JpaRepository<OrdemEmpresa, Long> {

}