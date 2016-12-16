package de.hsh.dbs2.imdb.logic.factory;

import java.sql.SQLException;
import java.util.List;
import de.hsh.dbs2.imdb.logic.model.Genre;
import de.hsh.dbs2.imdb.util.EntityManagerUtil;

import javax.persistence.EntityManager;

public class GenreFactory {

	public static List<String> getAllGenres() throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		List genres;
		try {
			em.getTransaction().begin();
			genres = em.createQuery("select g.genre from Genre g").getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

		return (List<String>) genres;
	}
	
	public static Genre find(String genre) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		Genre g;
		try {
			em.getTransaction().begin();
			g = (Genre) em.createQuery("select g from Genre g " +
					"where g.genre = :genre")
					.setParameter("genre", genre)
					.getSingleResult();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return g;
	}
	
}
