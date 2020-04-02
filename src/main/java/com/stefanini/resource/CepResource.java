package com.stefanini.resource;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stefanini.servico.CepServico;

@Path("viaCep")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CepResource {
	
	@Inject
    private CepServico cepServico;
	
	@GET
	@Path("/{cep}")
	public Response buscarEnderecoPorCep(@PathParam("cep") String cep) throws IOException {
		 return cepServico.buscarEnderecoPorCep(cep).map(cepDado -> Response.ok(cepDado).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
	}
	
	
}
