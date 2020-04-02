package com.stefanini.servico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class CepServico {

	public Optional<String> buscarEnderecoPorCep(String cep) throws IOException {
	
		try {
			URL urlReq = new URL("https://viacep.com.br/ws/" + cep + "/json/");
			HttpURLConnection con = (HttpURLConnection) urlReq.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			return Optional.ofNullable(response.toString());
			
		} catch (IOException e) {
			return Optional.empty();
		}
	}

}
