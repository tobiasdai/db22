package de.hsh.dbs2.imdb.logic.factory;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import de.hsh.dbs2.imdb.logic.model.Movie;
import de.hsh.dbs2.imdb.util.EntityManagerUtil;

import javax.persistence.EntityManager;

public class MovieFactory {
	
	public static List<Movie> getAllMovie() throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		List movies = em.createQuery("select m from Movie m").getResultList();
		em.close();
		return (List<Movie>) movies;
	}

	public static Movie findById(Long id) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		Movie movie = em.find(Movie.class, id);
		em.close();
		return movie;
	}
	
	public static List<Movie> search(String search) throws SQLException {
//		String sql = "{CALL IMPORTIEREN(?)}";
		return getAllMovie();
	}
	
	public static List<Movie> findByTitle(String title) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		List movies = em.createQuery("select m from Movie m " +
				"where m.title like :title")
				.setParameter("title", "%" + title + "%")
				.getResultList();
		em.close();
		return (List<Movie>) movies;
	}
	
	public static Movie add(Movie movie) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		em.persist(movie);
		em.getTransaction().commit();
		em.close();
		return movie;
	}
	
	public static void update(Movie m) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		Movie movie = em.find(Movie.class, m.getId());
		movie.setTitle(m.getTitle());
		movie.setYear(m.getYear());
		movie.setType(m.getType());
		movie.setGenres(m.getGenres());
		em.getTransaction().commit();
		em.close();
	}

	public static void clearGenre(Movie m) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		Movie movie = em.find(Movie.class, m.getId());
		movie.getGenres().clear();
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delete(Long id) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		Movie movie = em.find(Movie.class, id);
		em.remove(movie);
		em.getTransaction().commit();
		em.close();
	}
	
}
