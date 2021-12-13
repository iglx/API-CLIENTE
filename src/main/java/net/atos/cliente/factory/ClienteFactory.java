package net.atos.cliente.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import net.atos.cliente.domain.Cliente;
import net.atos.cliente.domain.Contato;
import net.atos.cliente.domain.Endereco;
import net.atos.cliente.repository.entity.ClienteEntity;
import net.atos.cliente.repository.entity.ContatoEntity;
import net.atos.cliente.repository.entity.ContatoPK;
import net.atos.cliente.repository.entity.EnderecoEntity;
import net.atos.cliente.repository.entity.EnderecoPK;

public class ClienteFactory {
	
	private Cliente vo;
	private ClienteEntity entity;
	
	public ClienteFactory(Cliente pVo) {
		this.entity = this.transformaClienteEntity(pVo);
		this.vo = pVo;
	}
	
	public ClienteFactory(ClienteEntity pEntity) {
		this.entity = pEntity;
		this.vo = this.transformaClienteVO(pEntity);
	}
	
	private Cliente transformaClienteVO(ClienteEntity clienteEntity) {
		
		Cliente clienteVO = new Cliente();
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

	private void construirContatoVO(Cliente cliente, AtomicInteger numeroContato, ContatoEntity contatoEntity) {
		
		Contato contatoVO = new Contato();
		contatoVO.setTipoContato(contatoEntity.getTipoContato());
		contatoVO.setNumero(contatoEntity.getNumero());

		cliente.addContato(contatoVO);
	
	}

	private void transformaEnderecoVO(Cliente cliente,EnderecoEntity enderecoEntity) {
		
		Endereco enderecoVO = new Endereco();
		enderecoVO.setId(enderecoEntity.getId());
		enderecoVO.setLogradouro(enderecoEntity.getLogradouro());
		enderecoVO.setBairro(enderecoEntity.getBairro());
		enderecoVO.setCidade(enderecoEntity.getCidade());
		enderecoVO.setEstado(enderecoEntity.getEstado());
		enderecoVO.setCep(enderecoEntity.getCep());
		enderecoVO.setComplemento(enderecoEntity.getComplemento());
		cliente.setEndereco(enderecoVO);
		
	}

	private ClienteEntity transformaClienteEntity(Cliente cliente) {
		
		ClienteEntity clienteEntity = new ClienteEntity();
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
	
	private void construirEndereco(ClienteEntity clienteEntity, AtomicInteger numeroEndereco, Endereco endereco) {
		EnderecoEntity enderecoEntity = new EnderecoEntity();
		enderecoEntity.setId(new EnderecoPK());
		enderecoEntity.getId().setNumeroEndereco(numeroEndereco.incrementAndGet());
		enderecoEntity.getId().setCliente(clienteEntity);
		enderecoEntity.setLogradouro(endereco.getLogradouro());
		enderecoEntity.setBairro(endereco.getBairro());
		enderecoEntity.setCidade(endereco.getCidade());
		enderecoEntity.setEstado(endereco.getEstado());
		enderecoEntity.setCep(endereco.getCep());
		enderecoEntity.setComplemento(endereco.getComplemento());

		clienteEntity.setEnderecos(enderecoEntity);
	}
	
	private void construirContato(ClienteEntity clienteEntity, AtomicInteger numeroContato, Contato contato) {
		ContatoEntity contatoEntity = new ContatoEntity();
		contatoEntity.setId(new ContatoPK());
		contatoEntity.getId().setNumeroContato(numeroContato.incrementAndGet());
		contatoEntity.getId().setCliente(clienteEntity);
		contatoEntity.setTipoContato(contato.getTipoContato());
		contatoEntity.setNumero(contato.getNumero());

		clienteEntity.addContato(contatoEntity);
	}

	public ClienteEntity toEntity() {
		return this.entity;
	}

	public Cliente toVO() {
		return vo;
	}
}
