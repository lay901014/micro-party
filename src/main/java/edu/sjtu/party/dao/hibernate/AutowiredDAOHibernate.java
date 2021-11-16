package edu.sjtu.party.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AutowiredDAOHibernate<T> extends BaseDAOHibernate<T> {

	@Autowired
	void init(@Qualifier("sessionFactory") SessionFactory sessionFactory) {

		this.setSessionFactory(sessionFactory);
	}

}
