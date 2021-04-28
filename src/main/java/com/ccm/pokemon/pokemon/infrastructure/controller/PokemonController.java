package com.ccm.pokemon.pokemon.infrastructure.controller;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.GetPokemonUseCase;
import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemonTypes.domain.exceptions.PokemonNotFoundException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Model
@Path("/pokemon")
public class PokemonController {
    @Inject
    GetPokemonUseCase getPokemonUseCase;

    @GET
    @Path("/types")
    public Response getTypes(@QueryParam("id") Integer id) {
        try {
            Pokemon result = getPokemonUseCase.getPokemonByPokemonId(new PokemonDto(id));

            return Response.status(200).entity("").build();
        } catch (PokemonNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (TimeoutException e) {
            return Response.status(408).entity(e.getMessage()).build();
        } catch (NetworkConnectionException e) {
            return Response.status(503).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Unexpected error. " + e.getMessage()).build();
        }
    }
}
