package com.stefanini.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PaginacaoPessoaDTO;
import com.stefanini.model.Pessoa;

/**
 * 
 * @author joaopedromilhome
 *
 */
public class PessoaDao extends GenericDao<Pessoa, Long> {

	public PessoaDao() {
		super(Pessoa.class);
	}

	/**
	 * Efetuando busca de Pessoa por email
	 * @param email
	 * @return
	 */
	public Optional<Pessoa> buscarPessoaPorEmail(String email){
		TypedQuery<Pessoa> q2 = entityManager.createQuery(" select p from Pessoa p where p.email=:email", Pessoa.class);
		q2.setParameter("email", email);
		return q2.getResultList().stream().findFirst();
	}
	
	
	/**
	 * Efetuando busca de Pessoa Cheia
	 */
	public Optional<List<Pessoa>> buscarPessoaCheia(){
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco ORDER BY p.nome", Pessoa.class);
		return Optional.ofNullable(query.getResultList());
	}
	
	
	
	/**
	 * busca pessoa por id
	 */
	public Optional<Pessoa> encontrarPessoaCheia(Long id) {
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco Where p.id = :id", Pessoa.class);
		query.setParameter("id", id);
		return Optional.ofNullable(query.getSingleResult());
	}
	
	
	/**
	 * paginação pessoa
	 */
	public PaginacaoPessoaDTO encontrarPessoasComPaginacao(Integer indiceAtual, Integer quantidadePorPagina){
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco ORDER BY p.nome", Pessoa.class);
		
		PaginacaoPessoaDTO pessoasPaginadas = new PaginacaoPessoaDTO();
		Integer qtdDeResult = query.getResultList().size(); 
		pessoasPaginadas.setQuantidadeDeResultados(qtdDeResult);
		
		query.setFirstResult(indiceAtual).setMaxResults(quantidadePorPagina);
		if(qtdDeResult % quantidadePorPagina == 0) {
			pessoasPaginadas.setTotalPaginas(qtdDeResult / quantidadePorPagina);
	      } else {
	    	pessoasPaginadas.setTotalPaginas((qtdDeResult / quantidadePorPagina) + 1);
	      }
		
		pessoasPaginadas.setResultado(query.getResultList());
				
		return pessoasPaginadas;
	}
	
	
}
