package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="T_Payments")
public class Payment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	private double payrollAmount;
	private double remainder;
	private String paymentDate;
	private String paymentReference;
	
	
	/*
	@OneToMany(mappedBy = "payment", cascade= {CascadeType.PERSIST})
	private List<Order> orders = new ArrayList<>();
	*/
	
	@OneToOne(mappedBy = "payment")
	private Order order = null;

	@ManyToOne
    @JoinColumn(name = "recipe_id")
	private Recipe recipe = null;
	
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public Payment() { 
		this.paymentReference = generateReference();
	}

	public Payment(int id, double payrollAmount, double remainder, String paymentDate, List<Order> orders) {
		super();
		this.id = id;
		this.payrollAmount = payrollAmount;
		this.remainder = remainder;
		this.paymentDate = paymentDate;
		// this.orders = orders;
		this.paymentReference = generateReference();
	}
	
	public Payment(double payrollAmount, double remainder, String paymentDate, List<Order> orders) {
		super();
		this.payrollAmount = payrollAmount;
		this.remainder = remainder;
		this.paymentDate = paymentDate;
		// this.orders = orders;
		this.paymentReference = generateReference();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPayrollAmount() {
		return payrollAmount;
	}

	public void setPayrollAmount(double payrollAmount) {
		this.payrollAmount = payrollAmount;
	}

	public double getRemainder() {
		return remainder;
	}

	public void setRemainder(double remainder) {
		this.remainder = remainder;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public List<Order> getOrders() {
		// return orders;
		return new ArrayList<Order>();
	}

	public void setOrders(List<Order> orders) {
		// this.orders = orders;
	}
	
	public void addOrder(Order order) {
		order.setPayment(this);
		// orders.add(order);
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Payment)) return false;
		
		if(this.id == ((Payment)obj).getId())
			return true;
		
		return false;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	
	public void updateDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		paymentDate = sdf.format(date);
	}
	

	public String generateReference() {
		if(paymentDate == null) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			paymentDate = sdf.format(date);
		}
		String reference = "#CMD@" + paymentDate.replaceAll("/", "").trim();
		return reference;
	}
	

}
