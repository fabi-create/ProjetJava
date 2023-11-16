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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="T_Orders")
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	/*
	@OneToMany(mappedBy = "order", cascade= {CascadeType.PERSIST})
	private List<Product> products = new ArrayList<>();
	*/
	
	/*
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "T_Order_ProductDetails",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "productdetails_id")
    )
    private List<Product> products = new ArrayList<>();
	*/
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductDetails> productDetails = new ArrayList<>();


	private String hourOrder;
    private double totalPrice;
    
    private String orderReference;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment = null;
    
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public Order() {
		this.orderReference = generateReference();
	}

	public Order(int id, List<Product> products, String hourOrder, double totalPrice) {
		super();
		this.id = id;
		//this.products = products;
		this.hourOrder = hourOrder;
		this.totalPrice = totalPrice;
		updateTotalPrice();
		this.orderReference = generateReference();
	}
	
	public Order(List<Product> products, String hourOrder, double totalPrice) {
		super();
		//this.products = products;
		this.hourOrder = hourOrder;
		this.totalPrice = totalPrice;
		updateTotalPrice();
		this.orderReference = generateReference();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		//return products;
		return new ArrayList<Product>();
	}

	public void setProducts(List<Product> products) {
		for(Product p : products) {
			p.setOrder(this);
		}
		// this.products = products;
		updateTotalPrice();
	}

	public String getHourOrder() {
		return hourOrder;
	}

	public void setHourOrder(String hourOrder) {
		this.hourOrder = hourOrder;
	}

	public double getTotalPrice() {
		updateTotalPrice();
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetails> orderItems) {
		for(ProductDetails p : orderItems) {
			p.setOrder(this);
		}
		this.productDetails = orderItems;
	}
	
	public void addProduct(Product product) {
		product.setOrder(this);
		// products.add(product);
		// product.getOrders().add(this);
		updateTotalPrice();
	}
	
	public void addProduct(Product product, int quantity) {
	    ProductDetails details = new ProductDetails(product, this, quantity);
	    productDetails.add(details);
	    product.addProductDetails(details); // Assurez-vous de maintenir la cohérence des relations
	    updateTotalPrice();
	}
	
	public void addProductDetails(ProductDetails details) {
		details.setOrder(this);
		productDetails.add(details);
	}
	
	private void updateTotalPrice() {
		double total = 0;
		/*
		for(Product product : products) {
			total += product.getPrice();
		}*/
		
		for(ProductDetails product : this.productDetails) {
			total += product.getTotal();
		}
		this.totalPrice = total;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Order)) return false;
		
		if(this.id == ((Order)obj).getId())
			return true;
		
		return false;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}
	
	public String generateReference() {
		if(hourOrder == null) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			hourOrder = sdf.format(date);
		}
		String reference = "#CMD@" + hourOrder.replaceAll("/", "").trim();
		return reference;
	}
    
}
