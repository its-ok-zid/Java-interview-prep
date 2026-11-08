package movie;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MovieService {
    // Removed the redundant "movies" map. One Map is enough.
    private final Map<String, Movie> movieById = new HashMap<>();
    private final Map<String, Set<Movie>> movieByGenres = new HashMap<>();

    private final TreeSet<Movie> topRatedMovies = new TreeSet<>((m1, m2) -> {
        int ratingCompare = Double.compare(m2.getRating(), m1.getRating());
        return ratingCompare != 0 ? ratingCompare : m1.getMovieId().compareTo(m2.getMovieId());
    });

    public void addMovie(Movie movie) {
        // Use the ID that is ALREADY attached to the movie object
        String id = movie.getMovieId();
        movieById.put(id, movie);

        // CRITICAL MISSING PIECE: Add it to your sorted tree!
        topRatedMovies.add(movie);

        for (String genre : movie.getGenres()) {
            movieByGenres
                .computeIfAbsent(genre, k -> new HashSet<>())
                .add(movie);
        }
    }

    public Movie findById(String id) {
        return movieById.get(id);
    }

    public Movie updateMovieRating(String id, double newRating) {
        Movie movie = movieById.get(id);
        if (movie == null) return null; // Added Null-safety check

        topRatedMovies.remove(movie);
        movie.setRating(newRating);
        topRatedMovies.add(movie);

        return movie;
    }

    public Set<Movie> findByGenres(String genre) {
        return movieByGenres.getOrDefault(genre, Collections.emptySet());
    }

    // Added a method to actually retrieve the top N movies to fulfill the requirement
    public List<Movie> getTopRatedMovies(int limit) {
        return topRatedMovies.stream().limit(limit).toList();
    }
}