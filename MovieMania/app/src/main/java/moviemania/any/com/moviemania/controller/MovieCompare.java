package moviemania.any.com.moviemania.controller;

import java.util.Comparator;

import moviemania.any.com.moviemania.model.Movie;

/**
 * Created by Юра on 13.02.2016.
 */
public class MovieCompare implements Comparator<Movie>
{

    @Override
    public int compare(Movie movie1, Movie movie2) {

        return movie1.getName().compareTo(movie2.getName());
    }
}
