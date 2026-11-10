package movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private MovieService movieService;
    private Movie spiderman;
    private Movie jumanji;

    @BeforeEach
    void setUp() {
        movieService = new MovieService();

        spiderman = new Movie();
        spiderman.setMovieId("ID-123");
        spiderman.setTitle("Spiderman Homecoming");
        spiderman.setRating(4.5);
        Set<String> spidermanGenres = new TreeSet<>();
        spidermanGenres.add("Action");
        spiderman.setGenres(spidermanGenres);

        jumanji = new Movie();
        jumanji.setMovieId("ID-456");
        jumanji.setTitle("Jumanji");
        jumanji.setRating(4.0);
        Set<String> jumanjiGenres = new TreeSet<>();
        jumanjiGenres.add("Comedy");
        jumanji.setGenres(jumanjiGenres);
    }

    @Test
    void testAddAndFindById() {
        movieService.addMovie(spiderman);
        Movie result = movieService.findById("ID-123");
        assertNotNull(result, "Movie should be retrievable by ID");
        assertEquals("Spiderman Homecoming", result.getTitle());
    }

    @Test
    void testFindByGenres() {
        movieService.addMovie(spiderman);
        movieService.addMovie(jumanji);

        Set<Movie> actionMovies = movieService.findByGenres("Action");
        assertEquals(1, actionMovies.size(), "Should find exactly 1 Action movie");
        assertTrue(actionMovies.contains(spiderman));

        Set<Movie> emptyGenre = movieService.findByGenres("Horror");
        assertTrue(emptyGenre.isEmpty(), "Non-existent genre should return empty set");
    }

    @Test
    void testTopRatedMoviesSorting() {
        movieService.addMovie(spiderman); // 4.5
        movieService.addMovie(jumanji);   // 4.0

        List<Movie> topMovies = movieService.getTopRatedMovies(2);
        assertEquals(2, topMovies.size());
        assertEquals("ID-123", topMovies.get(0).getMovieId(), "Spiderman (4.5) should be first");
        assertEquals("ID-456", topMovies.get(1).getMovieId(), "Jumanji (4.0) should be second");
    }

    @Test
    void testUpdateMovieRatingResortsTree() {
        movieService.addMovie(spiderman); // 4.5
        movieService.addMovie(jumanji);   // 4.0

        // Tank Spiderman's rating
        movieService.updateMovieRating("ID-123", 2.0);

        List<Movie> topMovies = movieService.getTopRatedMovies(2);
        assertEquals("ID-456", topMovies.get(0).getMovieId(), "Jumanji (4.0) should now be the top-rated movie");
        assertEquals("ID-123", topMovies.get(1).getMovieId(), "Spiderman (2.0) should be pushed to second place");
    }

    @Test
    void testUpdateNonExistentMovieReturnsNull() {
        assertNull(movieService.updateMovieRating("FAKE-ID", 5.0), "Safely return null for missing movies.");
    }
}