package org.outsera.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.outsera.dtos.IntervalItemDto;
import org.outsera.dtos.IntervalResponseDto;
import org.outsera.dtos.MovieDto;
import org.outsera.models.Movie;
import org.outsera.repositories.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {

    @Inject
    MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.listAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id);
    }

    public IntervalResponseDto findIntervals() {
        List<Movie> movies = findAll().stream()
                .filter(m -> "yes".equalsIgnoreCase(m.getWinner()))
                .collect(Collectors.toList());

        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : movies) {
            String[] producers = movie.getProducers().split(",| and ");
            for (String producer : producers) {
                producer = producer.trim();
                producerWins
                        .computeIfAbsent(producer, k -> new ArrayList<>())
                        .add(movie.getMovieYear());
            }
        }

        List<IntervalItemDto> intervalItems = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            if (years.size() < 2) continue;

            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);
                intervalItems.add(new IntervalItemDto(producer, interval, years.get(i - 1), years.get(i)));
            }
        }

        if (intervalItems.isEmpty()) {
            return new IntervalResponseDto(Collections.emptyList(), Collections.emptyList());
        }

        int minInterval = intervalItems.stream().mapToInt(IntervalItemDto::interval).min().orElse(0);
        int maxInterval = intervalItems.stream().mapToInt(IntervalItemDto::interval).max().orElse(0);

        List<IntervalItemDto> minList = intervalItems.stream()
                .filter(i -> i.interval() == minInterval)
                .collect(Collectors.toList());

        List<IntervalItemDto> maxList = intervalItems.stream()
                .filter(i -> i.interval() == maxInterval)
                .collect(Collectors.toList());

        return new IntervalResponseDto(minList, maxList);
    }

    public Movie createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setMovieYear(movieDto.year());
        movie.setProducers(movieDto.producers());
        movie.setTitle(movieDto.title());
        movie.setStudios(movieDto.studios());
        movie.setWinner(movieDto.winner());

        movieRepository.persist(movie);
        return movie;
    }

    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie movie = findById(id);

        if (movieDto.year() != null) {
            movie.setMovieYear(movieDto.year());
        }
        if (movieDto.title() != null) {
            movie.setTitle(movieDto.title());
        }
        if (movieDto.studios() != null) {
            movie.setStudios(movieDto.studios());
        }
        if (movieDto.producers() != null) {
            movie.setProducers(movieDto.producers());
        }
        if (movieDto.winner() != null) {
            movie.setWinner(movieDto.winner());
        }

        movieRepository.persist(movie);
        return movie;
    }

    public void deleteMovie(Long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
    } 
}
