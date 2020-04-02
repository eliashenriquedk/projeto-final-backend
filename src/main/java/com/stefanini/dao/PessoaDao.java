package com.stefanini.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PaginacaoGenericDto;
import com.stefanini.model.Pessoa;

/**
 * O Unico objetivo da Dao 
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
		return q2.getResultStream().findFirst();
	}
	
	
	/**
	 * Efetuando busca de Pessoa Cheia
	 */
	public Optional<List<Pessoa>> buscarPessoaCheia(){
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco ORDER BY p.nome", Pessoa.class);
		return Optional.ofNullable(query.getResultList());
	}
	
	
	
	
	public Optional<Pessoa> encontrarPessoaCheia(Long id) {
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco Where p.id = :id", Pessoa.class);
		query.setParameter("id", id);
		return Optional.ofNullable(query.getSingleResult());
	}
	
	
	/**
	 * Paginação de Pessoa
	 */
//	public PaginacaoGenericDTO<Pessoa> buscarPaginacaoPessoa(Integer indiceAtual, Integer quantidadePagina){
//		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco ORDER BY p.nome", Pessoa.class);
//		PaginacaoGenericDTO<Pessoa> page = new PaginacaoGenericDTO<Pessoa>();
//		page.setQuantidade(query.getResultList().size());
//		query.setFirstResult(indiceAtual).setMaxResults(quantidadePagina);
//		page.setResultado(query.getResultList());
//		
//		
//		page.setTotalPaginas(page.getResultado().size());
//		
//		
//		return page;
//	}
	
	
}
