package com.stefanini.servico;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.Valid;

import com.stefanini.dao.PerfilDao;
import com.stefanini.dto.PaginacaoPerfilDTO;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;

/**
 * 
 * Classe de servico, as regras de negocio devem estar nessa classe
 * 
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PerfilServico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilDao dao;

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;

	/**
	 * Salvar os dados de uma Perfil
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Perfil salvar(@Valid Perfil perfil) {
		perfil.setDataHoraAlteracao(LocalDateTime.now());
		return dao.salvar(perfil);
	}


	/**
	 * Validando se existe pessoa com email
	 */
	public Boolean validarPerfil(@Valid Perfil perfil){
		Optional<Perfil> perfil1 = dao.buscarPessoaPorNome(perfil.getNome());
		return perfil1.isEmpty();
	}

	/**
	 * Atualizar o dados de uma perfil
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Perfil atualizar(@Valid Perfil entity) {
		entity.setDataHoraAlteracao(LocalDateTime.now());
		return dao.atualizar(entity);
	}

	/**
	 * Remover uma perfil pelo id
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if(pessoaPerfilServico.buscarPessoaPerfil(null,id).count() == 0){
			dao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possivel remover o perfil");
	}

	/**
	 * Buscar uma lista de Perfil
	 */
	public Optional<List<Perfil>> getList() {
		return dao.getList();
	}

	/**
	 * Buscar uma Perfil pelo ID
	 */
	public Optional<Perfil> encontrar(Long id) {
		return dao.encontrar(id);
	}


	/**
	 * Buscar uma perfils paginados
	 */
	public PaginacaoPerfilDTO buscarPaginacaoPerfil(Integer indiceAtual, Integer quantidadePorPagina) {
		return dao.encontrarPerfilComPaginacao(indiceAtual, quantidadePorPagina);
	}
	
}
