package dao;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.model.Perfil;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PerfilDaoTeste {

	@Injectable
	  EntityManager em;

	  @Tested
	  PerfilDao perfilDao;
	
	
	
	
	  @Test
	  public void deveRetornarPaginados() {
		  perfilDao.encontrarPerfilComPaginacao(3, 6);
		  new Verifications() {{
			  em.createQuery(anyString, Perfil.class);
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveEncontrarPerfil() {
		  perfilDao.buscarPessoaPorNome("Dev");
		  new Verifications() {{
			  em.createQuery(anyString, Perfil.class);
			  times = 1;
		  }};
	  }
}
