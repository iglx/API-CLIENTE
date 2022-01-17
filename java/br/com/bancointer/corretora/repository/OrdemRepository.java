package br.com.bancointer.corretora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bancointer.corretora.entity.Ordem;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Long> {

}