package com.stefanini.dto;

import java.util.List;

import com.stefanini.model.Pessoa;

public class PaginacaoPessoaDTO {

	Integer totalPaginas;
	Integer quantidadeDeResultados;
	List<Pessoa> resultado;
	
	
	
	public Integer getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	
	public Integer getQuantidadeDeResultados() {
		return quantidadeDeResultados;
	}
	public void setQuantidadeDeResultados(Integer quantidadeDeResultados) {
		this.quantidadeDeResultados = quantidadeDeResultados;
	}
	
	public List<Pessoa> getResultado() {
		return resultado;
	}
	public void setResultado(List<Pessoa> resultado) {
		this.resultado = resultado;
	}
	
	
	
}
