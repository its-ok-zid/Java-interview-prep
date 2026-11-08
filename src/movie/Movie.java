package movie;

import java.util.Objects;
import java.util.Set;

public class Movie {

    private String  movieId;
    private String title;
    private Set<String> genres;
    private double rating;


    public void setMovieId(String movieId){
        this.movieId=movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMovieId(){
        return movieId;
    }

    public double getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieId, movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }

    @Override
    public String toString() {
        return "movie.Movie{id='" + movieId + "', title='" + title + "', rating=" + rating + "}";
    }
}
