package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.HibernateConnection;
import exceptions.DAOException;
import interfaces.IDao;
import models.Administrator;
import models.SuperAdministrator;

public class SuperAdminDaoImpl implements IDao<SuperAdministrator> {

	@Override
	public void create(SuperAdministrator entity) throws DAOException {
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
	public SuperAdministrator read(int id) throws DAOException {
		// TODO Auto-generated method stub
		SuperAdministrator superAdministrator = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			superAdministrator = session.find(SuperAdministrator.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return superAdministrator;
	}

	@Override
	public List<SuperAdministrator> list() throws DAOException {
		// TODO Auto-generated method stub
		List<SuperAdministrator> superAdministrators = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_SuperAdministrators");
			superAdministrators = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return superAdministrators;
	}

	@Override
	public void update(SuperAdministrator entity) throws DAOException {
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
			
			SuperAdministrator superAdministrator = read(id);
			if(superAdministrator != null) {
				session.delete(superAdministrator);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
