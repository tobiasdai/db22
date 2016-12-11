package de.hsh.dbs2.imdb.logic.model;

import javax.persistence.*;

@Entity
@Table(name = "N_CHARACTER")
public class MovieCharacter {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String character;

	@Column
	private String alias;

	@Column
	private int position;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
