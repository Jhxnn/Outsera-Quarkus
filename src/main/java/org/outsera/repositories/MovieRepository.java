package org.outsera.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.outsera.models.Movie;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

}
