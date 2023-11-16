package datas;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.HibernateConnection;

import dao.OrderDaoImpl;
import dao.PaymentDaoImpl;
import dao.ProductDaoImpl;
import dao.UserDaoImpl;
import exceptions.DAOException;
import factories.ConcreteFactory;
import factories.OrderFactory;
import factories.PaymentFactory;
import factories.ProductFactory;
import factories.UserFactory;
import models.Order;
import models.Payment;
import models.Product;
import models.User;

public class BaseAccess {
	
	private static BaseAccess instance = null;
	
	public static synchronized BaseAccess getInstance() {
		if (instance == null) {
            instance = new BaseAccess();
        }
		return instance;
	}
	
	private BaseAccess() {
		HibernateConnection hibernateConnection = HibernateConnection.getInstance();
		Session session = hibernateConnection.getSession();
		if(session.isOpen()) {
			System.out.println("Hibernate started successfully !");
		} else {
			System.out.println("Hibernate started failed !");
		}
	}
	
	
	// Les appels Dao
	
	/*
	 * 
	 *  User
	 * 
	 */
	
	public User readUser(String login, String password) {
		UserDaoImpl userDao  = ConcreteFactory.getFactory(UserFactory.class)
				.getUserDao(UserDaoImpl.class);
		User user = null;
		try {
			List<User> users = userDao.list();
			for(User u : users) {
				if(u.getLogin().equalsIgnoreCase(login) && u.getPassword().equalsIgnoreCase(password)) {
					user = u;
				}
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> getUsers() {
		UserDaoImpl userDao  = ConcreteFactory.getFactory(UserFactory.class)
				.getUserDao(UserDaoImpl.class);
		List<User> users = new ArrayList<>();
		try {
			users = userDao.list();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public User addUser(User user) {
		UserDaoImpl userDao = ConcreteFactory.getFactory(UserFactory.class)
				.getUserDao(UserDaoImpl.class);
		try {
			userDao.create(user);
			System.out.println("Une nouvel utilisateur est ajouté !");
		} catch (DAOException e) {
			
		}
		return user;
	}
	
	public User updateUser(User user) {
		UserDaoImpl userDao = ConcreteFactory.getFactory(UserFactory.class)
				.getUserDao(UserDaoImpl.class);
		try {
			userDao.update(user);
			System.out.println("Utilisateur est modifié !");
		} catch (DAOException e) {
			
		}
		return user;
	}
	
	public User deleteUser(User user) {
		UserDaoImpl userDao = ConcreteFactory.getFactory(UserFactory.class)
				.getUserDao(UserDaoImpl.class);
		try {
			userDao.delete(user.getId());
			System.out.println("Utilisateur est supprimé !");
		} catch (DAOException e) {
			
		}
		return user;
	}
	
	
	/*
	 * 
	 *  Product
	 * 
	 */
	
	public Product readProduct(int id) {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		Product product = null;
		try {
			product = productDao.read(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	
	public List<Product> getProducts() {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		List<Product> products = new ArrayList<>();
		try {
			products = productDao.list();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
	public Product addProduct(Product product) {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		try {
			productDao.create(product);
			System.out.println("Un nouvel article est ajouté !");
		} catch (DAOException e) {
			
		}
		return product;
	}
	
	public Product updateProduct(Product product) {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		try {
			productDao.update(product);
			System.out.println("Produit modifié !");
		} catch (DAOException e) {
			
		}
		return product;
	}
	
	public Product deleteProduct(Product product) {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		try {
			productDao.delete(product.getId());
			System.out.println("Produit supprimé !");
		} catch (DAOException e) {
			
		}
		return product;
	}
	
	/*
	 * 
	 * Order
	 * 
	 */
	
	public Order readOrder(int id) {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		Order order = null;
		try {
			order = orderDao.read(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
	
	public List<Order> getOrders() {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		List<Order> orders = new ArrayList<>();
		try {
			orders = orderDao.list();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}
	
	public Order addOrder(Order order) {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		try {
			orderDao.create(order);
			System.out.println("Une nouvelle commande est ajoutée !");
		} catch (DAOException e) {
			
		}
		return order;
	}
	
	public Order updateOrder(Order order) {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		try {
			orderDao.update(order);
			System.out.println("Commande modifiée !");
		} catch (DAOException e) {
			
		}
		return order;
	}
	
	public Order deleteOrder(Order order) {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		try {
			orderDao.delete(order.getId());
			System.out.println("Commande supprimée !");
		} catch (DAOException e) {
			
		}
		return order;
	}
	
	/*
	 * 
	 * Payment
	 * 
	 */
	
	public Payment readPayment(int id) {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		Payment payment = null;
		try {
			payment = paymentDao.read(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payment;
	}
	
	public List<Payment> getPayments() {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		List<Payment> payments = new ArrayList<>();
		try {
			payments = paymentDao.list();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payments;
	}
	
	public Payment addPayment(Payment payment) {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		try {
			paymentDao.create(payment);
			System.out.println("Un nouveau paiement est ajouté !");
		} catch (DAOException e) {
			
		}
		return payment;
	}
	
	public Payment updatePayment(Payment payment) {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		try {
			paymentDao.update(payment);
			System.out.println("Paiement modifié !");
		} catch (DAOException e) {
			
		}
		return payment;
	}
	
	public Payment deletePayment(Payment payment) {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		try {
			paymentDao.delete(payment.getId());
			System.out.println("Paiement supprimé !");
		} catch (DAOException e) {
			
		}
		return payment;
	}
}
