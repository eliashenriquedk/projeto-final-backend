package com.stefanini.servico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.validation.Valid;

import com.stefanini.dao.PessoaDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Pessoa;

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
public class PessoaServico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaDao dao;

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;
	
	@Inject
	private EnderecoServico enderecoServico;

	/**
	 * Salvar os dados de uma Pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa salvar(@Valid Pessoa pessoa) {
		List<Endereco> guardarEnderecos = pessoa.getEnderecos().stream().map(e -> e).collect(Collectors.toList());
		pessoa.getEnderecos().clear();
		
		if(Objects.nonNull(pessoa.getBase64Imagem()) && pessoa.getBase64Imagem().split(",").length > 1) {
			pessoa.setCaminhoImagem(this.salvarImagemAnexo(pessoa.getBase64Imagem(), pessoa.getEmail()));
		}
		
		Pessoa pessoaSalva = dao.salvar(pessoa);
		guardarEnderecos.forEach(e -> {
			e.setIdPessoa(pessoaSalva.getId());
			enderecoServico.salvar(e);
		});
		return pessoaSalva;
	}
	/**
	 * Validando se existe pessoa com email
	 */
	public Boolean validarPessoa(@Valid Pessoa pessoa){
		if(pessoa.getId() != null){
			Optional<Pessoa> encontrar = dao.encontrar(pessoa.getId());
			if(encontrar.get().getEmail().equals(pessoa.getEmail())){
				return Boolean.TRUE;
			}
		}
		Optional<Pessoa> pessoa1 = dao.buscarPessoaPorEmail(pessoa.getEmail());
		return pessoa1.isEmpty();
	}

	/**
	 * Atualizar o dados de uma pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Pessoa atualizar(@Valid Pessoa pessoa) {
		if(Objects.nonNull(pessoa.getBase64Imagem()) && pessoa.getBase64Imagem().split(",").length > 1) {
			pessoa.setCaminhoImagem(this.salvarImagemAnexo(pessoa.getBase64Imagem(), pessoa.getEmail()));
		}
		return dao.atualizar(pessoa);
	}

	/**
	 * Remover uma pessoa pelo id
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if(pessoaPerfilServico.buscarPessoaPerfil(id,null).count() == 0){
			dao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possivel remover a pessoa porque existem perfis vinculados a ela");
	}

	/**
	 * Buscar uma lista de Pessoa
	 */
	public Optional<List<Pessoa>> getList() {
		return dao.buscarPessoaCheia();
	}

	/**
	 * Buscar uma Pessoa pelo ID
	 */
//	@Override
	public Optional<Pessoa> encontrar(Long id) {
		return dao.encontrarPessoaCheia(id);
	}
	

	private String salvarImagemAnexo(String imageString, String email) {
		String caminho = "C:\\Users\\Henrique\\VSCode\\Projeto-front-bb\\hackaton-stefanini-estatico-hackaton\\src\\app\\imagens\\fotosPessoas\\";
	    String url = caminho + email + ".jpg";
	    imageString = imageString.split(",")[1];   
	    BufferedImage image = null;
	    byte[] imageByte;
	    
	    if(!new File(caminho).exists()) {
		      new File(caminho).mkdir();
		    }
	    
	    try {
	        imageByte = Base64.getDecoder().decode(imageString.getBytes());
	        
	        ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);
	        image = ImageIO.read(bais);
	        bais.close();
	        ImageIO.write(image, "JPG", new File(url));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return email + ".jpg";
	}
	


	
	

	
//	public PaginacaoGenericDTO<Pessoa> buscarPaginacaoPessoa(Integer indiceAtual, Integer quantidadePagina) {
//		return dao.buscarPaginacaoPessoa(indiceAtual, quantidadePagina);
//	}
	
	
}
