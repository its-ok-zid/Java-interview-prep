package movie;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MovieService movieService = new MovieService();
        Movie movie1 = new Movie();

        movie1.setMovieId(String.valueOf(UUID.randomUUID()));
        Set<String> Movie1Genres = new TreeSet<>();
        Movie1Genres.add("Action");
        Movie1Genres.add("Comedy");
        movie1.setGenres(Movie1Genres);
        movie1.setRating(4.5);
        movie1.setTitle("Spiderman Homecoming");
        movieService.addMovie(movie1);

        Movie movie2 = new Movie();

        movie2.setMovieId(String.valueOf(UUID.randomUUID()));
        Set<String> Movie2Genres = new TreeSet<>();
        Movie2Genres.add("Filler");
        Movie2Genres.add("Comedy");
        movie2.setGenres(Movie2Genres);
        movie2.setRating(4);
        movie2.setTitle("Jumanji");
        movieService.addMovie(movie2);

        Movie movie3 = new Movie();

        movie3.setMovieId(String.valueOf(UUID.randomUUID()));
        Set<String> Movie3Genres = new TreeSet<>();
        Movie3Genres.add("Sci-fi");
        Movie3Genres.add("Comedy");
        movie3.setGenres(Movie3Genres);
        movie3.setRating(5.0);
        movie3.setTitle(" Matrix");
        movieService.addMovie(movie3);


        System.out.println("movies1 :");
        System.out.println(movieService.findById(movie1.getMovieId()));

        System.out.println("movies2 :");
        System.out.println(movieService.findById(movie2.getMovieId()));

        System.out.println("movies3 :");
        System.out.println(movieService.findById(movie3.getMovieId()));


        System.out.println("Updating movie " + movie1.getMovieId() + " rating:");
        movieService.updateMovieRating(movie1.getMovieId(), 2.3);

        System.out.println("Finding movie by genre: Comedy");

        System.out.println(movieService.findByGenres("Filler"));

    }
}