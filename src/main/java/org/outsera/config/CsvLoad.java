package org.outsera.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.runtime.StartupEvent;
import org.outsera.models.Movie;
import org.outsera.repositories.MovieRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@ApplicationScoped
public class CsvLoad {

    @Inject
    MovieRepository movieRepository;

    public void onStart(@Observes StartupEvent ev) {
        loadCsv();
    }

    @Transactional
    void loadCsv() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/movies.csv")))) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(";");
                Movie movie = new Movie();
                movie.setMovieYear(Integer.parseInt(partes[0]));
                movie.setTitle(partes[1]);
                movie.setStudios(partes[2]);
                movie.setProducers(partes[3]);
                movie.setWinner(partes.length >= 5 ? partes[4] : "no");

                movieRepository.persist(movie);
            }

            System.out.println("CSV importado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
