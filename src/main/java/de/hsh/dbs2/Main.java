package de.hsh.dbs2;


import de.hsh.dbs2.model.Genre;
import de.hsh.dbs2.model.Movie;
import de.hsh.dbs2.model.MovieCharacter;
import de.hsh.dbs2.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

public class Main {

	private static EntityManagerFactory emf;

	private static void init() {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();

			em.createQuery("delete from MovieCharacter").executeUpdate();
			em.createQuery("delete from Movie").executeUpdate();
			em.createQuery("delete from Person").executeUpdate();
			em.createQuery("delete from Genre").executeUpdate();

			Movie movie = new Movie();
			movie.setTitle("star war");
			movie.setYear(1988);
			movie.setType("T");

			Person person = new Person();
			person.setName("James");
			person.setSex("m");

			Genre genre = new Genre();
			genre.setGenre("Action");
			Set<Movie> movies = new HashSet<Movie>();
			movies.add(movie);
			genre.setMovies(movies);
			Set<Genre> genres = new HashSet<Genre>();
			genres.add(genre);
			movie.setGenres(genres);

			MovieCharacter character = new MovieCharacter();
			character.setCharacter("Hero");
			character.setMovie(movie);
			character.setPerson(person);

			em.persist(movie);
			em.persist(genre);
			em.persist(person);
			em.persist(character);

			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("moviedb");
		init();
		emf.close();
	}

}
