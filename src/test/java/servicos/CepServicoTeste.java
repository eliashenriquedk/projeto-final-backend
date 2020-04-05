package servicos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import com.stefanini.servico.CepServico;

import mockit.Tested;

public class CepServicoTeste {

	
	@Tested
	  CepServico cepServico;
	
	
	  @Test
	  public void deveBuscarCep() throws IOException {
		  Optional<String> cep = cepServico.buscarEnderecoPorCep("72319217");
		  assertTrue(cep.isPresent());
	  }
	  
	  @Test
	  public void deveMostrarFalhaBuscarCep() throws IOException {
		  Optional<String> cep = cepServico.buscarEnderecoPorCep("723");
		  assertTrue(cep.isEmpty());
	  }
}
