package de.hsh.dbs2.imdb.util;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("moviedb");

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void closeEmf() {
		emf.close();
	}

}
