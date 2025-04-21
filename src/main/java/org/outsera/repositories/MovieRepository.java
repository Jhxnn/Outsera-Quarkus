package org.outsera.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.outsera.models.Movie;


@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

}
