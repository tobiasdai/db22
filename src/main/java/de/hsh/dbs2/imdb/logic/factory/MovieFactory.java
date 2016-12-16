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
		List movies;
		try {
			em.getTransaction().begin();
			movies = em.createQuery("select m from Movie m").getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return (List<Movie>) movies;
	}

	public static Movie findById(Long id) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		Movie movie;
		try {
			em.getTransaction().begin();
			movie = em.find(Movie.class, id);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return movie;
	}
	
	public static List<Movie> findByTitle(String title) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		List movies;
		try {
			em.getTransaction().begin();
			movies = em.createQuery("select m from Movie m " +
					"where upper(m.title) like upper(:title)")
					.setParameter("title", "%" + title + "%")
					.getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return (List<Movie>) movies;
	}
	
	public static Movie add(Movie movie) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(movie);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return movie;
	}
	
	public static void update(Movie m) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(m);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
	}
	
	public static void delete(Long id) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			Movie movie = em.find(Movie.class, id);
			em.remove(movie);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
	}
	
}
