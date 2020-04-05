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

import com.stefanini.dao.EnderecoDao;
import com.stefanini.model.Endereco;
import com.stefanini.servico.EnderecoServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class EnderecoServicoTeste {

  @Injectable
  EntityManager em;

  @Tested
  EnderecoServico endServico;

  @Injectable
  @Mocked
  EnderecoDao enderecoDao;

  private Endereco endereco;

  private List<Endereco> listaEnderecos;

  @Before
  public void beforeTeste() {
    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setBairro("samambaia");
    endereco.setCep("72319216");
    endereco.setComplemento("casa");
    endereco.setIdPessoa(5L);
    endereco.setLocalidade("brasilia");
    endereco.setLogradouro("naosei");
    endereco.setUf("DF");

    listaEnderecos = new ArrayList<Endereco>();
    listaEnderecos.add(endereco);
  }

  @Test
  public void deveRetornarEnderecos() {
	  new Expectations() {{ 
		  enderecoDao.getList();
		  result = Optional.of(listaEnderecos);
	  }};
	  Optional<List<Endereco>> retornoDoMetodo = endServico.getList();
	  assertTrue(retornoDoMetodo.isPresent());
	  assertFalse(retornoDoMetodo.get().isEmpty());
	  
	  assertEquals("72319216", retornoDoMetodo.get().get(0).getCep());
	  new Verifications() {{
		  enderecoDao.getList();
		  times = 1;
	  }};
  }
  
  
  @Test
  public void deveRetornarUmEndereco() {
	  new Expectations() {{ 
		  enderecoDao.encontrar(anyLong);
		  result = Optional.of(endereco);
	  }};
	  Optional<Endereco> retornoDoMetodo = endServico.encontrar(2L);
	  assertTrue(retornoDoMetodo.isPresent());
	  assertEquals("72319216", retornoDoMetodo.get().getCep());
	  
	  new Verifications() {{ //util para metodos voids que nao tem retorno
		  enderecoDao.encontrar(anyLong);
		  times = 1;
	  }};
  }
  
  @Test
  public void deveRemoverUmEndereco() {
	  endServico.remover(2L);
	  new Verifications() {{ //util para metodos voids que nao tem retorno
		  enderecoDao.remover(anyLong);
		  times = 1;
	  }};
  }

  
  @Test
  public void deveAtualizarUmEndereco() {
	  endServico.atualizar(endereco);
	  new Verifications() {{ 
		  enderecoDao.atualizar((@Valid Endereco) any);
		  times = 1;
	  }};
  }

  @Test
  public void deveSalvarUmEndereco() {
	  endServico.salvar(endereco);
	  new Verifications() {{ 
		  enderecoDao.salvar((@Valid Endereco) any);
		  times = 1;
	  }};
  }
  
  
}