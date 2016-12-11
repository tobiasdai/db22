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
		List persons = em.createQuery("select p from Person p " +
				"where p.name like :name")
				.setParameter("name", "%" + name + "%")
				.getResultList();
		em.close();
		return (List<Person>) persons;
    }
    
    public static Person findById(long id) throws SQLException {
		EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
		Person person = em.find(Person.class, id);
		em.close();
		return person;
	}
}
