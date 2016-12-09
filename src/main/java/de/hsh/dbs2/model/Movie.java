package de.hsh.dbs2.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "N_MOVIE")
public class Movie {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private String type;

	@ManyToMany()
	@JoinTable(name = "MOVIE_GENRE",
			joinColumns = @JoinColumn(name = "MOVIE_ID"),
			inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
	)
	private Set<Genre> genres = new HashSet<Genre>();

	@OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
	private Set<MovieCharacter> characters = new HashSet<MovieCharacter>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Set<MovieCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<MovieCharacter> characters) {
		this.characters = characters;
	}
}
