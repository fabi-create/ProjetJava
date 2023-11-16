package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnection {

	private static HibernateConnection instance = new HibernateConnection();
	
	public static HibernateConnection getInstance() {
		return instance;
	}
	
	private Session session = null;
	private SessionFactory factory = null;
	
	private HibernateConnection() {
		StandardServiceRegistry ssr = 
				new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		
		factory = meta.getSessionFactoryBuilder().build();
		
		session = factory.openSession();
	}

	public Session getSession() {
		if(session == null) 
			new HibernateConnection();
		return session;
	}
	
	public void closeSession() {
		if(session != null) {
			session.close();
			session = null;
		}
	}
	
	
	public void closeSessionFactory() {
		if(factory != null) {
			closeSession();
			factory.close();
			session = null;
		}
	}
	
}
	              