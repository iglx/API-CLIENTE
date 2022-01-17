package br.com.bancointer.corretora.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bancointer.corretora.dto.InvestimentoDTO;
import br.com.bancointer.corretora.dto.OrdemDTO;
import br.com.bancointer.corretora.dto.OrdemEmpresaDTO;
import br.com.bancointer.corretora.entity.Empresa;
import br.com.bancointer.corretora.entity.Ordem;
import br.com.bancointer.corretora.entity.OrdemEmpresa;
import br.com.bancointer.corretora.repository.EmpresaRepository;
import br.com.bancointer.corretora.repository.OrdemEmpresaRepository;
import br.com.bancointer.corretora.repository.OrdemRepository;

@Service
public class OrdemEmpresaService {

	@Autowired
	private OrdemEmpresaRepository ordemEmprsaRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private OrdemRepository ordemRepository;

	private double valordividido = 0;
	private double troco = 0;

	@Transactional
	public List<OrdemEmpresaDTO> findAll() {
		List<OrdemEmpresa> list = ordemEmprsaRepository.findAll();
		return list.stream().map(x -> new OrdemEmpresaDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public List<OrdemEmpresaDTO> insert(OrdemDTO dto) {

		valordividido = (dto.getValorInvestimento() / dto.getNumeroEmpresa());
		List<Empresa> listEmpresas = empresaRepository.findByEmpresas(valordividido);
		List<OrdemEmpresaDTO> listOrdensEmpresasDTO = new ArrayList<OrdemEmpresaDTO>();
		if (listEmpresas.size() >= dto.getNumeroEmpresa()) {
			Ordem ordem = new Ordem(null, dto.getCpf(), dto.getValorInvestimento(), dto.getNumeroEmpresa(), null);
			ordem = ordemRepository.save(ordem);

			Collections.shuffle(listEmpresas);
			List<Empresa> listEmpresasEscolhidas = new ArrayList<Empresa>();

			for (int i = 0; i < ordem.getNumeroEmpresa(); i++) {
				listEmpresasEscolhidas.add(listEmpresas.get(i));
			}

			listOrdensEmpresasDTO = compraAcoes(listEmpresasEscolhidas, ordem);
		} else {
			throw new BadRequestException(
					"Não é possivel dividir o valor investido de forma homogenea, escolha uma quantidade menor de empresas");
		}

		return listOrdensEmpresasDTO;
	}

	@Transactional
	private List<OrdemEmpresaDTO> compraAcoes(List<Empresa> listEmpresasEscolhidas, Ordem ordem) {

		List<OrdemEmpresaDTO> ordensEmpresasDTO = new ArrayList<OrdemEmpresaDTO>();
		Map<Integer, Integer> mapQtd = new HashMap<Integer, Integer>();
		int quantidade = 0;
		for (int i = 0; i < ordem.getNumeroEmpresa(); i++) {
			double valor = valordividido;
			quantidade = 0;

			while (valor >= listEmpresasEscolhidas.get(i).getValor()) {
				valor = (valor - listEmpresasEscolhidas.get(i).getValor());
				quantidade++;
			}
			mapQtd.put(i, quantidade);
			troco = (troco + valor);
		}
		if (troco > 0) {
			troco = verificaTroco(ordem, listEmpresasEscolhidas, mapQtd);
		}

		Ordem ordemEncontrada = ordemRepository.getById(ordem.getId());

		ordemEncontrada.setTroco(troco);
		ordemRepository.saveAndFlush(ordemEncontrada);

		for (int i = 0; i < ordem.getNumeroEmpresa(); i++) {
			Double valorTotal = (mapQtd.get(i).intValue() * listEmpresasEscolhidas.get(i).getValor());
			OrdemEmpresa ordemEmpresa = new OrdemEmpresa(null, mapQtd.get(i).longValue(), valorTotal, ordem,
					listEmpresasEscolhidas.get(i));
			ordemEmpresa = ordemEmprsaRepository.save(ordemEmpresa);
			ordensEmpresasDTO.add(new OrdemEmpresaDTO(ordemEmpresa));
		}

		return ordensEmpresasDTO;
	}

	private Double verificaTroco(Ordem ordem, List<Empresa> listEmpresasEscolhidas, Map<Integer, Integer> mapQtd) {
		int quantidade;
		for (int i = 0; i < ordem.getNumeroEmpresa(); i++) {
			quantidade = 0;
			while (troco >= listEmpresasEscolhidas.get(i).getValor()) {
				quantidade++;
				troco = (troco - listEmpresasEscolhidas.get(i).getValor());
			}
			if (quantidade > 0) {
				mapQtd.put(i, mapQtd.get(i).intValue() + quantidade);
			}
		}
		return troco;
	}

	public List<InvestimentoDTO> verInvestimento() {

		List<OrdemEmpresa> list = ordemEmprsaRepository.findAll();

		List<InvestimentoDTO> listInvestimento = new ArrayList<InvestimentoDTO>();
		for (int i = 0; i < list.size(); i++) {
			listInvestimento.add(new InvestimentoDTO(list.get(i).getId(), list.get(i).getEmpresa().getId(),
					list.get(i).getEmpresa().getNome(), list.get(i).getQuantidade(), list.get(i).getValorTotal(),
					list.get(i).getOrdem().getTroco()));
		}

		if (listInvestimento.size() > 1) {
			int val = 1;
			for (int i = 0; i < listInvestimento.size(); i++) {
				for (int j = val; j < listInvestimento.size(); j++) {
					if ((listInvestimento.get(i).getIdEmpresa() == listInvestimento.get(j).getIdEmpresa())
							&& (listInvestimento.get(i).getIdOrdemEmpresa() != listInvestimento.get(j)
									.getIdOrdemEmpresa())) {
						listInvestimento.get(i).setQuantidade(
								listInvestimento.get(i).getQuantidade() + listInvestimento.get(j).getQuantidade());
						listInvestimento.get(i).setValorTotal(
								listInvestimento.get(i).getValorTotal() + listInvestimento.get(j).getValorTotal());
						listInvestimento.get(i)
								.setTroco(listInvestimento.get(i).getTroco() + listInvestimento.get(j).getTroco());
						listInvestimento.remove(j);

					}
				}
				val++;
			}
		}

		return listInvestimento;
	}

}
