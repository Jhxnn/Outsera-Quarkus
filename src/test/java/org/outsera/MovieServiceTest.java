package org.outsera;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.outsera.dtos.IntervalItemDto;
import org.outsera.dtos.IntervalResponseDto;
import org.outsera.models.Movie;
import org.outsera.repositories.MovieRepository;
import org.outsera.services.MovieService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieServiceTest {

    @Inject
    MovieService movieService;

    @Inject
    MovieRepository movieRepository;

    @BeforeAll
    @Transactional
    void setup() {
        movieRepository.deleteAll();

        movieRepository.persist(new Movie(2000, "Filme A", "Estudio", "Produtor 1", "yes"));
        movieRepository.persist(new Movie(2002, "Filme B", "Estudio", "Produtor 1", "yes"));
        movieRepository.persist(new Movie(2010, "Filme C", "Estudio", "Produtor 2", "yes"));
        movieRepository.persist(new Movie(2020, "Filme D", "Estudio", "Produtor 2", "yes"));
        movieRepository.persist(new Movie(2022, "Filme E", "Estudio", "Produtor 2", "yes"));
        movieRepository.persist(new Movie(2021, "Filme F", "Estudio", "Produtor 3", "no"));
    }

    @Test
    void testFindIntervals() {
        IntervalResponseDto response = movieService.findIntervals();

        List<IntervalItemDto> min = response.min();
        List<IntervalItemDto> max = response.max();

        assertNotNull(min);
        assertNotNull(max);

        boolean hasMin = min.stream()
                .anyMatch(i -> i.producer().equals("Produtor 2") && i.interval() == 2 && i.previousWin() == 2020 && i.followingWin() == 2022);
        assertTrue(hasMin);

        boolean hasMax = max.stream()
                .anyMatch(i -> i.producer().equals("Produtor 2") && i.interval() == 10 && i.previousWin() == 2010 && i.followingWin() == 2020);
        assertTrue(hasMax);
    }
}
