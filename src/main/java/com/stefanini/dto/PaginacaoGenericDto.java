package com.stefanini.dto;

import java.util.List;

public class PaginacaoGenericDto<T> {
	
	Integer totalPaginas;
	Integer quantidade;
	List<T> resultado;
	
	
	
	public Integer getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public List<T> getResultado() {
		return resultado;
	}
	public void setResultado(List<T> resultado) {
		this.resultado = resultado;
	}
	
	

	
	
	
}
