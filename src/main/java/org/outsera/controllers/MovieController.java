package org.outsera.controllers;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.outsera.dtos.IntervalResponseDto;
import org.outsera.dtos.MovieDto;
import org.outsera.models.Movie;
import org.outsera.services.MovieService;

import java.util.List;
import java.util.UUID;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieController {

    @Inject
    MovieService movieService;

    @Operation(description = "Lista todos os filmes")
    @GET
    public Response findAll() {
        List<Movie> movies = movieService.findAll();
        return Response.ok(movies).build();
    }

    @Operation(description = "Lista filme por ID")
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(movieService.findById(id)).build();
    }

    @Operation(description = "Lista produtores com menor e maior intervalo de vitória")
    @GET
    @Path("/interval")
    public Response findMaxInterval() {
        IntervalResponseDto result = movieService.findIntervals();
        return Response.ok(result).build();
    }

    @Operation(description = "Cria um filme")
    @POST
    public Response createMovie(MovieDto movieDto) {
        Movie movie = movieService.createMovie(movieDto);
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }


    @Operation(description = "Atualiza um filme")
    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, MovieDto movieDto) {
        Movie updated = movieService.updateMovie(id, movieDto);
        return Response.ok(updated).build();
    }

    @Operation(description = "Deleta um filme")
    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieService.deleteMovie(id);
        return Response.noContent().build();
    }
}
