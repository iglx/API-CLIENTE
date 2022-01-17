package net.atos.api.cliente.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import net.atos.api.cliente.domain.ContatoVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.PessoaVO;
import net.atos.api.cliente.repository.entity.ContatoEntity;
import net.atos.api.cliente.repository.entity.ContatoPK;
import net.atos.api.cliente.repository.entity.EnderecoEntity;
import net.atos.api.cliente.repository.entity.PessoaFisicaEntity;

public class PessoaFisicaFactory {
	
	private PessoaVO vo;
	private PessoaFisicaEntity entity;
	
	public PessoaFisicaFactory(PessoaVO pVo) {
		this.entity = this.transformaEmPessoaFisicaEntity(pVo);
		this.vo = pVo;
	}
	
	public PessoaFisicaFactory(PessoaFisicaEntity pEntity) {
		this.entity = pEntity;
		this.vo = this.transformaEmPessoaVO(pEntity);
	}
	
	private PessoaVO transformaEmPessoaVO(PessoaFisicaEntity pessoaEntity) {
		
		PessoaVO pessoaVO = new PessoaVO();
		pessoaVO.setDataCadastro(pessoaEntity.getDataCadastro());
		pessoaVO.setDataAlteracao(pessoaEntity.getDataAlteracao());
		pessoaVO.setStatusPessoaEnum(pessoaEntity.getStatusPessoaEnum());
		pessoaVO.setTipoPessoaEnum(pessoaEntity.getTipoPessoaEnum());
		pessoaVO.setNome(pessoaEntity.getNome());
		pessoaVO.setEmail(pessoaEntity.getEmail());
		pessoaVO.setNascimento(pessoaEntity.getNascimento());
		pessoaVO.setNrCpf(pessoaEntity.getNrCpf());
	
		AtomicInteger numeroContato = new AtomicInteger();
		
		this.transformaEmEnderecoVO(pessoaVO,pessoaEntity.getEndereco());
		
		List<ContatoEntity> contatos = Optional.ofNullable(pessoaEntity.getContatos()).orElseGet(ArrayList::new);
		contatos.stream().forEach(contato ->
			this.transformaEmContatoVO(pessoaVO, numeroContato, contato));
		
		return pessoaVO;
		
	}

	private void transformaEmContatoVO(PessoaVO pessoa, AtomicInteger numeroContato, ContatoEntity contatoEntity) {
		
		ContatoVO contatoVO = new ContatoVO();
		contatoVO.setTipoContato(contatoEntity.getTipoContato());
		contatoVO.setNumero(contatoEntity.getNumero());

		pessoa.addContato(contatoVO);
	
	}

	private void transformaEmEnderecoVO(PessoaVO pessoa,EnderecoEntity enderecoEntity) {
		
		EnderecoVO enderecoVO = new EnderecoVO();
		
		enderecoVO.setLogradouro(enderecoEntity.getLogradouro());
		enderecoVO.setBairro(enderecoEntity.getBairro());
		enderecoVO.setCidade(enderecoEntity.getCidade());
		enderecoVO.setEstado(enderecoEntity.getEstado());
		enderecoVO.setCep(enderecoEntity.getCep());
		enderecoVO.setComplemento(enderecoEntity.getComplemento());
		pessoa.setEndereco(enderecoVO);
		
	}

	private PessoaFisicaEntity transformaEmPessoaFisicaEntity(PessoaVO pessoaVO) {
		
		PessoaFisicaEntity pessoaEntity = new PessoaFisicaEntity();
		pessoaEntity.setDataCadastro(pessoaVO.getDataCadastro());
		pessoaEntity.setDataAlteracao(pessoaVO.getDataAlteracao());
		pessoaEntity.setStatusPessoaEnum(pessoaVO.getStatusPessoaEnum());
		pessoaEntity.setNome(pessoaVO.getNome());
		pessoaEntity.setEmail(pessoaVO.getEmail());
		pessoaEntity.setNascimento(pessoaVO.getNascimento());
		pessoaEntity.setNrCpf(pessoaVO.getNrCpf());
	
		AtomicInteger numeroContato = new AtomicInteger();
		
		this.transformaEmEnderecoEntity(pessoaEntity, pessoaVO.getEndereco());
		
		pessoaVO.getContatos().stream().forEach(contato ->
			this.transformaEmContatoEntity(pessoaEntity, numeroContato, contato));
		
		return pessoaEntity;
	}
	
	private void transformaEmEnderecoEntity(PessoaFisicaEntity pessoaEntity, EnderecoVO enderecoVO) {
		EnderecoEntity enderecoEntity = new EnderecoEntity();	
		enderecoEntity.setLogradouro(enderecoVO.getLogradouro());
		enderecoEntity.setBairro(enderecoVO.getBairro());
		enderecoEntity.setCidade(enderecoVO.getCidade());
		enderecoEntity.setEstado(enderecoVO.getEstado());
		enderecoEntity.setCep(enderecoVO.getCep());
		enderecoEntity.setComplemento(enderecoVO.getComplemento());

		pessoaEntity.setEndereco(enderecoEntity);
	}
	
	private void transformaEmContatoEntity(PessoaFisicaEntity pessoaEntity, AtomicInteger numeroContato, ContatoVO contatoVO) {
		ContatoEntity contatoEntity = new ContatoEntity();
		contatoEntity.setId(new ContatoPK());
		contatoEntity.getId().setNumeroContato(numeroContato.incrementAndGet());
		contatoEntity.getId().setPessoa(pessoaEntity);
		contatoEntity.setTipoContato(contatoVO.getTipoContato());
		contatoEntity.setNumero(contatoVO.getNumero());

		pessoaEntity.addContato(contatoEntity);
	}

	public PessoaFisicaEntity toEntity() {
		return this.entity;
	}

	public PessoaVO toVO() {
		return vo;
	}
}
