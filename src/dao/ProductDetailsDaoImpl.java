package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.HibernateConnection;
import exceptions.DAOException;
import interfaces.IDao;
import models.ProductDetails;

public class ProductDetailsDaoImpl implements IDao<ProductDetails> {

	@Override
	public void create(ProductDetails entity) throws DAOException {
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
	public ProductDetails read(int id) throws DAOException {
		// TODO Auto-generated method stub
		ProductDetails productDetails = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			productDetails = session.find(ProductDetails.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return productDetails;
	}

	@Override
	public List<ProductDetails> list() throws DAOException {
		// TODO Auto-generated method stub
		List<ProductDetails> productsDetails = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_ProductDetails");
			productsDetails = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return productsDetails;
	}

	@Override
	public void update(ProductDetails entity) throws DAOException {
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
			
			ProductDetails productDetails = read(id);
			if(productDetails != null) {
				session.delete(productDetails);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
