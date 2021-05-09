package com.ccm.user.user.infrastructure.controllers;

import com.ccm.user.user.application.usecases.AddFavouritePokemonUseCase;
import com.ccm.user.user.application.usecases.AddUserUseCase;
import com.ccm.user.user.application.dto.UserDTO;
import com.ccm.user.user.application.dto.UserFavouritePokemonDTO;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;
import org.jboss.logmanager.Level;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Model
@Path("/user")
public class UserController {

    @Inject
    AddFavouritePokemonUseCase addFavouritePokemonUseCase;

    @Inject
    AddUserUseCase addUserUseCase;

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @GET
    @Path("/addFavouritePokemon")
    public Response addFavouritePokemon(@HeaderParam ("id") int userId, @QueryParam("id") int pokemonId) {
        try {
            addFavouritePokemonUseCase.addFavouritePokemon(new UserFavouritePokemonDTO(
                pokemonId, userId)
            );
            logger.log(Level.INFO, "Adding pokemon " + pokemonId + " as favourite for the user " + userId);
            return Response.status(200).build();
        } catch (FavouritePokemonAlreadyExistsException e) {
            return Response.status(409).entity(e.getMessage()).build();
        } catch (UserNotFoundException e) {
            return Response.status(403).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Unexpected error. " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/addUser")
    public Response addUser(@QueryParam("name") String name, @QueryParam("userId") int userId) {
        try {
            addUserUseCase.createUser(new UserDTO(name, userId));
            logger.log(Level.INFO, "User " + name + " with id " + userId + " created");
            return Response.status(200).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(403).entity(e.getMessage()).build();
        }
    }
}
