package master;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import db.HibernateConnection;
import models.Order;
import models.Payment;
import models.Product;
import models.Recipe;

public class Launcher {
	private static Testeur testeur = new Testeur();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HibernateConnection hibernateConnection = HibernateConnection.getInstance();
		Session session = hibernateConnection.getSession();
		if(session.isOpen()) {
			System.out.println("Hibernate started successfully !");
			
			testeur.creerAdministrator();
			testeur.creerChef();
			testeur.creerRestaurateur();
			
			Product produit = new Product("Pringles", 2900, "Chips salées et pimentée", 2, "");
			Product produit2 = new Product("Jus OR", 1500, "Jus d'orange", 5, "");
			
			List<Product> products1 = new ArrayList<>();
			products1.add(produit);
			products1.add(produit2);
			products1.add(produit);
			
			Product produit3 = new Product("Binto", 500, "Biscuits au chocolat", 10, "");
			Product produit4 = new Product("Biscreme", 125, "Biscuit chocolaté", 13, "");
			
			List<Product> products2 = new ArrayList<>();
			products2.add(produit3);
			products2.add(produit4);
			
			testeur.creerProduct("Pringles", 2900, "Chips salées et pimentée", 2);
			testeur.creerProduct("Jus OR", 1500, "Jus d'orange", 5);
			testeur.creerProduct("Binto", 500, "Biscuits au chocolat", 10);
			testeur.creerProduct("Biscreme", 125, "Biscuit chocolaté", 13);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dateString = sdf.format(date);
			
			
			Order order = new Order(products1, dateString, 4400);
			// order.addProduct(produit);
			// order.addProduct(produit2);
			
			Order order2 = new Order(products2, dateString, 2300);
			// order2.addProduct(produit3);
			// order2.addProduct(produit4);
			

			testeur.creerOrder(products1);
			testeur.creerOrder(products2);
			
			List<Order> orders = new ArrayList<>();
			orders.add(order);
			orders.add(order2);
			
			Payment pay = new Payment(10555, 2500, dateString, orders);
			pay.addOrder(order);
			pay.addOrder(order2);
			
			List<Payment> payments = new ArrayList<>();
			payments.add(pay);
			
			Recipe recipe = new Recipe(payments);
			recipe.addPayment(pay);
			
			// testeur.creerRecipe(payments);
			// testeur.creerRecipe(recipe);
			
			session.close();
		} else 
		System.out.println("Echec !");
	}
}
