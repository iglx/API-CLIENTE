package net.atos.cliente.factory;

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
	
	public ClienteFactory(Cliente vo) {
		this.entity = this.transformaEntity(vo);
	}
	
	public ClienteFactory(ClienteEntity entity) {
		throw new IllegalAccessError();
	}
	
	private ClienteEntity transformaEntity(Cliente cliente) {
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setDataCadastro(cliente.getDataCadastro());
		clienteEntity.setDataAlteracao(cliente.getDataAlteracao());
		clienteEntity.setStatus(cliente.getStatus());
		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setNascimento(cliente.getNascimento());
		
		AtomicInteger numeroEndereco = new AtomicInteger();
		cliente.getEnderecos().stream().forEach(endereco ->
			this.construirEndereco(clienteEntity, numeroEndereco, endereco));

		AtomicInteger numeroContato = new AtomicInteger();
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

		clienteEntity.addEndereco(enderecoEntity);
	}
	
	private void construirContato(ClienteEntity clienteEntity, AtomicInteger numeroContato, Contato contato) {
		ContatoEntity contatoEntity = new ContatoEntity();
		contatoEntity.setId(new ContatoPK());
		contatoEntity.getId().setNumeroContato(numeroContato.incrementAndGet());
		contatoEntity.getId().setCliente(clienteEntity);
		contatoEntity.setTelefone(contato.getTelefone());
		contatoEntity.setCelular(contato.getCelular());
		
		clienteEntity.addContato(contatoEntity);
	}

	public ClienteEntity toEntity() {
		return this.entity;
	}

	public Cliente getVo() {
		return vo;
	}
}
