package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.HibernateConnection;
import exceptions.DAOException;
import interfaces.IDao;
import models.Product;

public class ProductDaoImpl implements IDao<Product> {

	@Override
	public void create(Product entity) throws DAOException {
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
	public Product read(int id) throws DAOException {
		// TODO Auto-generated method stub
		Product product = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			product = session.find(Product.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return product;
	}

	@Override
	public List<Product> list() throws DAOException {
		// TODO Auto-generated method stub
		List<Product> products = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_Products");
			products = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return products;
	}

	@Override
	public void update(Product entity) throws DAOException {
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
			
			Product product = read(id);
			if(product != null) {
				session.delete(product);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
