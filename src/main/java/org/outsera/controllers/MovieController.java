package org.outsera.controllers;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @GET
    public Response findAll() {
        List<Movie> movies = movieService.findAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(movieService.findById(id)).build();
    }

    @GET
    @Path("/interval")
    public Response findMaxInterval() {
        IntervalResponseDto result = movieService.findIntervals();
        return Response.ok(result).build();
    }

    @POST
    public Response createMovie(MovieDto movieDto) {
        Movie movie = movieService.createMovie(movieDto);
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, MovieDto movieDto) {
        Movie updated = movieService.updateMovie(id, movieDto);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieService.deleteMovie(id);
        return Response.noContent().build();
    }
}
