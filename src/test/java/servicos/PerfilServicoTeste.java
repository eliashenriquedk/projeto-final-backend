package servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.model.Perfil;
import com.stefanini.servico.PerfilServico;
import com.stefanini.servico.PessoaPerfilServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PerfilServicoTeste {
	
	@Injectable
	  EntityManager em;

	  @Tested
	  PerfilServico perfilServico;

	  @Injectable
	  @Mocked
	  PerfilDao perfilDao;

	  @Injectable
	  PessoaPerfilServico psServico;
	  
	  private Perfil perfil;

	  private List<Perfil> listaPerfils;

	  @Before
	  public void beforeTeste() {
	    perfil = new Perfil();
	    perfil.setId(1L);
	    perfil.setNome("teste");
	    perfil.setDescricao("testeDesc");

	    listaPerfils = new ArrayList<Perfil>();
	    listaPerfils.add(perfil);
	  }

	  @Test
	  public void deveRetornarPerfils() {
		  new Expectations() {{ 
			  perfilDao.getList();
			  result = Optional.of(listaPerfils);
		  }};
		  Optional<List<Perfil>> retornoDoMetodo = perfilServico.getList();
		  assertTrue(retornoDoMetodo.isPresent());
		  assertFalse(retornoDoMetodo.get().isEmpty());
		  
		  assertEquals("teste", retornoDoMetodo.get().get(0).getNome());
		  new Verifications() {{
			  perfilDao.getList();
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveEncontrarPerfil() {
		  new Expectations() {{ 
			  perfilDao.encontrar(anyLong);
			  result = Optional.of(perfil);
		  }};
		  Optional<Perfil> retornoDoMetodo = perfilServico.encontrar(2L);
		  assertTrue(retornoDoMetodo.isPresent());
		  assertEquals("testeDesc", retornoDoMetodo.get().getDescricao());
		  
		  new Verifications() {{ //util para metodos voids que nao tem retorno
			  perfilDao.encontrar(anyLong);
			  times = 1;
		  }};
	  }
	  
	  
	  @Test
	  public void deveAtualizarUmPerfil() {
		  perfilServico.atualizar(perfil);
		  new Verifications() {{ 
			  perfilDao.atualizar((@Valid Perfil) any);
			  times = 1;
		  }};
	  }

	  @Test
	  public void deveSalvarUmPerfil() {
		  perfilServico.salvar(perfil);
		  new Verifications() {{ 
			  perfilDao.salvar((@Valid Perfil) any);
			  times = 1;
		  }};
	  }
	  
	  @Test
	  public void deveValidarPerfil() {
		  new Expectations() {{ 
			  perfilDao.buscarPessoaPorNome(anyString);
			  result = Optional.of(perfil);
		  }};
		  Boolean retornoDoMetodo = perfilServico.validarPerfil(perfil);
		  assertFalse(retornoDoMetodo);
	  }
}
