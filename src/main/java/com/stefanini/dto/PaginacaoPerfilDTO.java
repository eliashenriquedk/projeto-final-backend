package com.stefanini.dto;

import java.util.List;

import com.stefanini.model.Perfil;

public class PaginacaoPerfilDTO {

	Integer totalPaginas;
	Integer quantidadeDeResultados;
	List<Perfil> resultado;
	
	
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
	public List<Perfil> getResultado() {
		return resultado;
	}
	public void setResultado(List<Perfil> resultado) {
		this.resultado = resultado;
	}
	
	
	
	
}
