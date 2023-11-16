package master;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.AdministratorDaoImpl;
import dao.ChefDaoImpl;
import dao.OrderDaoImpl;
import dao.PaymentDaoImpl;
import dao.ProductDaoImpl;
import dao.RecipeDaoImpl;
import dao.RestaurateurDaoImpl;
import exceptions.DAOException;
import factories.AdministratorFactory;
import factories.ChefFactory;
import factories.ConcreteFactory;
import factories.OrderFactory;
import factories.PaymentFactory;
import factories.ProductFactory;
import factories.RecipeFactory;
import factories.RestaurateurFactory;
import models.Administrator;
import models.Chef;
import models.Order;
import models.Payment;
import models.Product;
import models.Recipe;
import models.Restaurateur;

public class Testeur {
	
	public Testeur() {}

	public Administrator creerAdministrator() {
		AdministratorDaoImpl adminDao = ConcreteFactory.getFactory(AdministratorFactory.class)
				.getAdministratorDao(AdministratorDaoImpl.class);
		Administrator admin = null;
		
		try {
			admin = new Administrator("AdminLN", "AdminFN", "admin", "admin", "Administrator");
			adminDao.create(admin);
			System.out.println("Une nouvelle admin est ajouté !");
		} catch (DAOException e) {
			
		}
		
		return admin;
	}
	
	public Chef creerChef() {
		ChefDaoImpl chefDao = ConcreteFactory.getFactory(ChefFactory.class)
				.getChefDao(ChefDaoImpl.class);
		Chef chef = null;
		
		try {
			chef = new Chef("ChefLN", "ChefFN", "chef", "chef", "Chef");
			chefDao.create(chef);
			System.out.println("Un nouveau chef est ajouté !");
		} catch (DAOException e) {
			
		}
		
		return chef;
	}
	
	public Restaurateur creerRestaurateur() {
		RestaurateurDaoImpl restaurateurDao = ConcreteFactory.getFactory(RestaurateurFactory.class)
				.getRestaurateurDao(RestaurateurDaoImpl.class);
		Restaurateur restaurateur = null;
		
		try {
			restaurateur = new Restaurateur("RestaurateurLN", "RestaurateurFN", "resto", "resto", "Restaurateur");
			restaurateurDao.create(restaurateur);
			System.out.println("Un nouveau restaurateur est ajouté !");
		} catch (DAOException e) {
			
		}
		
		return restaurateur;
	}
	
	public Product creerProduct(String name, float price, String description, int quantity) {
		ProductDaoImpl productDao = ConcreteFactory.getFactory(ProductFactory.class)
				.getProductDao(ProductDaoImpl.class);
		Product product = null;
		
		try {
			product = new Product(name, price, description, quantity, "");
			productDao.create(product);
			System.out.println("Un nouveau produit est ajouté !");
		} catch (DAOException e) {
			
		}
		
		return product;
	}
	
	public Order creerOrder(List<Product> products) {
		OrderDaoImpl orderDao = ConcreteFactory.getFactory(OrderFactory.class)
				.getOrderDao(OrderDaoImpl.class);
		Order order = null;
		
		double totalPrice = 0;
		for(Product product : products) {
			totalPrice += product.getPrice();
		}
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateString = sdf.format(date);
		
		try {
			order = new Order(products, dateString, totalPrice);
			// order.setProducts(products);
			orderDao.create(order);
			System.out.println("Une nouvelle commande est ajoutée !");
		} catch (DAOException e) {
			
		}
		
		return order;
	}
	
	public Payment creerPayment(double payrollAmount, double remainder, String paymentDate, List<Order> orders) {
		PaymentDaoImpl paymentDao = ConcreteFactory.getFactory(PaymentFactory.class)
				.getPaymentDao(PaymentDaoImpl.class);
		Payment payment = null;
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateString = sdf.format(date);
		
		try {
			payment = new Payment(payrollAmount, remainder, dateString, orders);
			paymentDao.create(payment);
			System.out.println("Une nouvelle commande est ajoutée !");
		} catch (DAOException e) {
			
		}
		
		return payment;
	}

	
	public Recipe creerRecipe(List<Payment> payments) {
		RecipeDaoImpl recipeDao = ConcreteFactory.getFactory(RecipeFactory.class)
				.getRecipeDao(RecipeDaoImpl.class);
		Recipe recipe = null;
		
		try {
			recipe = new Recipe(payments);
			
			for(Payment pay : payments) 
				recipe.addPayment(pay);
			
			recipeDao.create(recipe);
			System.out.println("Une nouvelle recette est ajoutée !");
		} catch (DAOException e) {
			
		}
		
		return recipe;
	}
	
	public Recipe creerRecipe(Recipe recipe) {
		RecipeDaoImpl recipeDao = ConcreteFactory.getFactory(RecipeFactory.class)
				.getRecipeDao(RecipeDaoImpl.class);
		try {
			recipeDao.create(recipe);
			System.out.println("Une nouvelle recette est ajoutée !");
		} catch (DAOException e) {
			
		}
		
		return recipe;
	}
}
