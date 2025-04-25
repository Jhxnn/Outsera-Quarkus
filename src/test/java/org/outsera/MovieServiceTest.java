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

    @Test
    void testFindIntervalsMatchesExpectedJsonFile() throws Exception {
        IntervalResponseDto response = movieService.findIntervals();

        String expectedJson = new String(getClass().getClassLoader()
                .getResourceAsStream("expected_intervals.json").readAllBytes());

        var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String actualJson = mapper.writeValueAsString(response);

        var expectedNode = mapper.readTree(expectedJson);
        var actualNode = mapper.readTree(actualJson);

        assertEquals(expectedNode, actualNode, "A resposta da API não está igual ao JSON");
    }

}
