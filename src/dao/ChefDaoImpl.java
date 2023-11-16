package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.HibernateConnection;
import exceptions.DAOException;
import interfaces.IDao;
import models.Chef;

public class ChefDaoImpl implements IDao<Chef> {

	@Override
	public void create(Chef entity) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			
			session.persist(entity);
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + " : " + e.getMessage());
		}
	}

	@Override
	public Chef read(int id) throws DAOException {
		// TODO Auto-generated method stub
		Chef chef = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			chef = session.find(Chef.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return chef;
	}

	@Override
	public List<Chef> list() throws DAOException {
		// TODO Auto-generated method stub
		List<Chef> chefs = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_Chefs");
			chefs = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return chefs;
	}

	@Override
	public void update(Chef entity) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			session.merge(entity);
			transaction.commit();
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			
			Chef chef = read(id);
			if(chef != null) {
				session.delete(chef);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
