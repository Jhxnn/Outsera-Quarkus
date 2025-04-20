package org.outsera.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "movies")
public class Movie extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long movieId;

    private Integer movieYear;

    private String title;

    private String studios;

    private String producers;

    private String winner;

    public String getStudios() {
        return studios;
    }

    public Movie(Integer movieYear, String title, String studios, String producers, String winner) {
        this.movieYear = movieYear;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Movie(String winner, String producers, String studios, String title, Integer movieYear) {
        this.winner = winner;
        this.producers = producers;
        this.studios = studios;
        this.title = title;
        this.movieYear = movieYear;
    }
    public Movie(){

    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
        this.movieYear = movieYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}