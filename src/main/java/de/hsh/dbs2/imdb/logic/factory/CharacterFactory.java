package de.hsh.dbs2.imdb.logic.factory;

import java.sql.SQLException;
import de.hsh.dbs2.imdb.logic.model.MovieCharacter;
import de.hsh.dbs2.imdb.util.EntityManagerUtil;

import javax.persistence.EntityManager;

public class CharacterFactory {
	
	public static void delete(long movieId) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		em.createQuery("delete from MovieCharacter c " +
				"where c.movie.id = :movieId")
				.setParameter("movieId", movieId)
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	public static void add(MovieCharacter character) {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		em.getTransaction().begin();
		em.persist(character);
		em.getTransaction().commit();
		em.close();
	}
	
}
