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
import models.ProductImage;

public class ProductImageDaoImpl implements IDao<ProductImage>{

	@Override
	public void create(ProductImage entity) throws DAOException {
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
	public ProductImage read(int id) throws DAOException {
		// TODO Auto-generated method stub
		ProductImage productImage = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			productImage = session.find(ProductImage.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return productImage;
	}

	@Override
	public List<ProductImage> list() throws DAOException {
		// TODO Auto-generated method stub
		List<ProductImage> productImages = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_ProductImages");
			productImages = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return productImages;
	}

	@Override
	public void update(ProductImage entity) throws DAOException {
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
			
			ProductImage productImage = read(id);
			if(productImage != null) {
				session.delete(productImage);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
