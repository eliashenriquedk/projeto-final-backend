package com.stefanini.dao;

import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PaginacaoPerfilDTO;
import com.stefanini.model.Perfil;

/**
 * O Unico objetivo da Dao 
 * @author joaopedromilhome
 *
 */
public class PerfilDao extends GenericDao<Perfil, Long> {

	public PerfilDao() {
		super(Perfil.class);
	}

	/**
	 * Efetuando busca de Pessoa por email
	 */
	public Optional<Perfil> buscarPessoaPorNome(String nome){
		TypedQuery<Perfil> q2 =
				entityManager.createQuery(" select p from Perfil p where p.nome=:nome", Perfil.class);
		q2.setParameter("nome", nome);
		return q2.getResultStream().findFirst();
	}
	
	
	/**
	 * paginação de perfil
	 */
	public PaginacaoPerfilDTO encontrarPerfilComPaginacao(Integer indiceAtual, Integer quantidadePorPagina){
		TypedQuery<Perfil> query = entityManager.createQuery("select p from Perfil p", Perfil.class);
		
		PaginacaoPerfilDTO perfilsPaginados = new PaginacaoPerfilDTO();
		Integer qdtDeResultados = query.getResultList().size(); 
		perfilsPaginados.setQuantidadeDeResultados(qdtDeResultados);
		
		query.setFirstResult(indiceAtual).setMaxResults(quantidadePorPagina);
		if(qdtDeResultados % quantidadePorPagina == 0) {
			perfilsPaginados.setTotalPaginas(qdtDeResultados / quantidadePorPagina);
	      } else {
	    	perfilsPaginados.setTotalPaginas((qdtDeResultados / quantidadePorPagina) + 1);
	      }
		
		perfilsPaginados.setResultado(query.getResultList());
		return perfilsPaginados;
	}
	

}
