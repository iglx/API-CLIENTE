package net.atos.cliente.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import net.atos.cliente.domain.PessoaVO;
import net.atos.cliente.domain.ContatoVO;
import net.atos.cliente.domain.EnderecoVO;
import net.atos.cliente.repository.entity.PessoaEntity;
import net.atos.cliente.repository.entity.ContatoEntity;
import net.atos.cliente.repository.entity.ContatoPK;
import net.atos.cliente.repository.entity.EnderecoEntity;

public class ClienteFactory {
	
	private PessoaVO vo;
	private PessoaEntity entity;
	
	public ClienteFactory(PessoaVO pVo) {
		this.entity = this.transformaClienteEntity(pVo);
		this.vo = pVo;
	}
	
	public ClienteFactory(PessoaEntity pEntity) {
		this.entity = pEntity;
		this.vo = this.transformaClienteVO(pEntity);
	}
	
	private PessoaVO transformaClienteVO(PessoaEntity clienteEntity) {
		
		PessoaVO clienteVO = new PessoaVO();
		clienteVO.setDataCadastro(clienteEntity.getDataCadastro());
		clienteVO.setDataAlteracao(clienteEntity.getDataAlteracao());
		clienteVO.setStatus(clienteEntity.getStatus());
		clienteVO.setNome(clienteEntity.getNome());
		clienteVO.setCpf(clienteEntity.getCpf());
		clienteVO.setEmail(clienteEntity.getEmail());
		clienteVO.setNascimento(clienteEntity.getNascimento());
	
		AtomicInteger numeroContato = new AtomicInteger();
		
		this.transformaEnderecoVO(clienteVO,clienteEntity.getEnderecos());
		
		List<ContatoEntity> contatos = Optional.ofNullable(clienteEntity.getContatos()).orElseGet(ArrayList::new);
		contatos.stream().forEach(contato ->
			this.construirContatoVO(clienteVO, numeroContato, contato));
		
		return clienteVO;
		
	}

	private void construirContatoVO(PessoaVO cliente, AtomicInteger numeroContato, ContatoEntity contatoEntity) {
		
		ContatoVO contatoVO = new ContatoVO();
		contatoVO.setTipoContato(contatoEntity.getTipoContato());
		contatoVO.setNumero(contatoEntity.getNumero());

		cliente.addContato(contatoVO);
	
	}

	private void transformaEnderecoVO(PessoaVO cliente,EnderecoEntity enderecoEntity) {
		
		EnderecoVO enderecoVO = new EnderecoVO();
		enderecoVO.setLogradouro(enderecoEntity.getLogradouro());
		enderecoVO.setBairro(enderecoEntity.getBairro());
		enderecoVO.setCidade(enderecoEntity.getCidade());
		enderecoVO.setEstado(enderecoEntity.getEstado());
		enderecoVO.setCep(enderecoEntity.getCep());
		enderecoVO.setComplemento(enderecoEntity.getComplemento());
		cliente.setEndereco(enderecoVO);
		
	}

	private PessoaEntity transformaClienteEntity(PessoaVO cliente) {
		
		PessoaEntity clienteEntity = new PessoaEntity();
		clienteEntity.setDataCadastro(cliente.getDataCadastro());
		clienteEntity.setDataAlteracao(cliente.getDataAlteracao());
		clienteEntity.setStatus(cliente.getStatus());
		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setNascimento(cliente.getNascimento());
	
		AtomicInteger numeroContato = new AtomicInteger();
		
		this.construirEndereco(clienteEntity, numeroContato, cliente.getEndereco());
		
		cliente.getContatos().stream().forEach(contato ->
			this.construirContato(clienteEntity, numeroContato, contato));
		
		return clienteEntity;
	}
	
	private void construirEndereco(PessoaEntity clienteEntity, AtomicInteger numeroEndereco, EnderecoVO endereco) {
		EnderecoEntity enderecoEntity = new EnderecoEntity();
		enderecoEntity.setLogradouro(endereco.getLogradouro());
		enderecoEntity.setBairro(endereco.getBairro());
		enderecoEntity.setCidade(endereco.getCidade());
		enderecoEntity.setEstado(endereco.getEstado());
		enderecoEntity.setCep(endereco.getCep());
		enderecoEntity.setComplemento(endereco.getComplemento());

		clienteEntity.setEnderecos(enderecoEntity);
	}
	
	private void construirContato(PessoaEntity clienteEntity, AtomicInteger numeroContato, ContatoVO contato) {
		ContatoEntity contatoEntity = new ContatoEntity();
		contatoEntity.setId(new ContatoPK());
		contatoEntity.getId().setNumeroContato(numeroContato.incrementAndGet());
		contatoEntity.getId().setCliente(clienteEntity);
		contatoEntity.setTipoContato(contato.getTipoContato());
		contatoEntity.setNumero(contato.getNumero());

		clienteEntity.addContato(contatoEntity);
	}

	public PessoaEntity toEntity() {
		return this.entity;
	}

	public PessoaVO toVO() {
		return vo;
	}
}
