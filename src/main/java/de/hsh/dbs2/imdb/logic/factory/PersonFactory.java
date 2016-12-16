package de.hsh.dbs2.imdb.logic.factory;

import de.hsh.dbs2.imdb.logic.model.Person;
import de.hsh.dbs2.imdb.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dais on 2016-11-23.
 */
public class PersonFactory{
	
    public static List<Person> findByName(String name) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		List persons;
		try {
			em.getTransaction().begin();
			persons = em.createQuery("select p from Person p " +
					"where upper(p.name) like upper(:name)")
					.setParameter("name", "%" + name + "%")
					.getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

		return (List<Person>) persons;
    }
}
