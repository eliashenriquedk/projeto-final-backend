package dao;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Pessoa;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PessoaDaoTeste {

	@Injectable
	  EntityManager em;

	  @Tested
	  PessoaDao pessoaDao;
	
	  
	  @Test
	  public void deveRetornarPaginados() {
		  pessoaDao.encontrarPessoasComPaginacao(3, 6);
		  new Verifications() {{
			  em.createQuery(anyString, Pessoa.class);
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveEncontrarPessoaCheia() {
		  pessoaDao.encontrarPessoaCheia(1L);
		  new Verifications() {{
			  em.createQuery(anyString, Pessoa.class);
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveEncontrarTodasPessoasCheias() {
		  pessoaDao.buscarPessoaCheia();
		  new Verifications() {{
			  em.createQuery(anyString, Pessoa.class);
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveEncontrarPorEmail() {
		  pessoaDao.buscarPessoaPorEmail("elias");
		  new Verifications() {{
			  em.createQuery(anyString, Pessoa.class);
			  times = 1;
		  }};
	  }
}
