package net.atos.servicos.factory;

import net.atos.servicos.domain.ServicoVO;
import net.atos.servicos.repository.entity.ServicoEntity;

public class ServicoFactory {
	
	private ServicoVO servicoVO;
	private ServicoEntity servicoEntity;
	
	public ServicoFactory(ServicoVO pServicoVO) {
		this.servicoEntity = this.transformaEntity(pServicoVO);
		this.servicoVO = pServicoVO;
	}
	
	public ServicoFactory(ServicoEntity pServicoEntity) {
		this.servicoEntity = pServicoEntity;
		this.servicoVO = this.transformaVO(pServicoEntity);
	}
	
	private ServicoVO transformaVO(ServicoEntity entity) {
		ServicoVO vo = new ServicoVO();
		vo.setId(entity.getId());
		vo.setIdCliente(entity.getIdCliente());
		vo.setTipoCliente(entity.getTipoCliente());
		vo.setValor(entity.getValor());
		vo.setDataEmissao(entity.getDataEmissao());
		return vo;
	}
	
	private ServicoEntity transformaEntity(ServicoVO vo) {
		ServicoEntity entity = new ServicoEntity();
		entity.setId(vo.getId());
		entity.setIdCliente(vo.getIdCliente());
		entity.setTipoCliente(vo.getTipoCliente());
		entity.setValor(vo.getValor());
		entity.setDataEmissao(vo.getDataEmissao());
		return entity;
	}

	public ServicoEntity toEntity() {
		return this.servicoEntity;
	}
	
	public ServicoVO toVO() {
		return this.servicoVO;
	}

}
