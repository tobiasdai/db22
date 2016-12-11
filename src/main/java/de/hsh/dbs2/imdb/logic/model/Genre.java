package de.hsh.dbs2.imdb.logic.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "N_GENRE")
public class Genre {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String genre;

	@ManyToMany(mappedBy = "genres")
	private Set<Movie> movies = new HashSet<Movie>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return genre;
	}
}
