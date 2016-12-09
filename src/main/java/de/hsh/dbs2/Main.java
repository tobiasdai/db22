package de.hsh.dbs2;


import de.hsh.dbs2.model.Genre;
import de.hsh.dbs2.model.Movie;
import de.hsh.dbs2.model.MovieCharacter;
import de.hsh.dbs2.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
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

			Movie movie2 = new Movie();
			movie2.setTitle("Blue Sky");
			movie2.setYear(1988);
			movie2.setType("T");

			Person person = new Person();
			person.setName("James");
			person.setSex("m");

			Genre genre = new Genre();
			genre.setGenre("Action");

			Genre genre2 = new Genre();
			genre2.setGenre("Music");
//			Set<Movie> movies = new HashSet<Movie>();
//			movies.add(movie);
//			genre.setMovies(movies);
			Set<Genre> genres = new HashSet<Genre>();
			genres.add(genre);
			movie.setGenres(genres);

			MovieCharacter character = new MovieCharacter();
			character.setCharacter("Hero");
			character.setMovie(movie);
			character.setPerson(person);

			em.persist(movie);
			em.persist(genre);
			em.persist(genre2);
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

	private static void delete() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery(
				"select m from Movie m " +
						"where m.title = 'star war'"
		);
		Movie movie = (Movie) q.getSingleResult();
		em.remove(movie);
		em.getTransaction().commit();
		em.close();
	}

	private static void find() {
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery(
				"select m from Movie m " +
						"where m.title = 'star war'"
		);
		Movie movie = (Movie) q.getSingleResult();
		System.out.println("genre: " + new ArrayList<Genre>(movie.getGenres()).get(0).getGenre());

		Query q1 = em.createQuery(
				"select g from Genre g " +
						"where g.genre = 'Action'"
		);
		Genre genre = (Genre) q1.getSingleResult();
		System.out.println("movie: " + new ArrayList<Movie>(genre.getMovies()).get(0).getTitle());

		em.close();
	}

	private static void update(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery(
				"select m from Movie m " +
						"where m.title = 'star war'"
		);
		Movie movie = (Movie) q.getSingleResult();

		Query q1 = em.createQuery(
				"select g from Genre g " +
						"where g.genre = 'Music'"
		);
		Genre genre = (Genre) q1.getSingleResult();

		Set<Genre> genres = movie.getGenres();
		genres.add(genre);
		movie.setGenres(genres);
		em.getTransaction().commit();
		System.out.println("movie: " + new ArrayList<Movie>(genre.getMovies()).get(0).getTitle());
	}

	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("moviedb");
		init();
		//find();
		//delete();
		update();
		emf.close();
	}

}
