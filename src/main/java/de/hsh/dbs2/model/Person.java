package de.hsh.dbs2.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "N_PERSON")
public class Person {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private String sex;

	@OneToMany(mappedBy = "person")
	private Set<MovieCharacter> characters = new HashSet<MovieCharacter>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Set<MovieCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<MovieCharacter> characters) {
		this.characters = characters;
	}
}
