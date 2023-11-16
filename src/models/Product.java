package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity(name="T_Products")
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	private String name;
	private double price;
	private String description;
	private int quantity;
	private String category;
	
	private boolean selected;
	
	/*
	@ManyToOne
    @JoinColumn(name = "order_id")
	private Order order = null;
	*/
	// @ManyToMany(mappedBy = "products")
    // private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
    private List<ProductDetails> productDetailsItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();
	
	
	/*
	public Order getOrder() {
		return order;
	}
	*/
	/*
	public void setOrder(Order order) {
		this.order = order;
	}
	*/
	
	public void addProductImage(ProductImage productImage) {
        productImages.add(productImage);
        productImage.setProduct(this);
    }
	
	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public void setOrder(Order order) {
		// this.orders.add(order);
	}
	
	public List<Order> getOrders() {
		// return orders;
		return new ArrayList<Order>();
	}

	public void setOrders(List<Order> orders) {
		// this.orders = orders;
	}

	public Product() {}

	public Product(int id, String name, double price, String description, int quantity, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.category = category;
	}
	
	public Product(String name, double price, String description, int quantity, String category) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.category = category;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addProductDetails(ProductDetails details) {
		details.setProduct(this);
		productDetailsItems.add(details);
	}
	
	@Override
    public String toString() {
        return "Produit [id= "+ id + "intitule=" + name + ", prix=" + price + ", description=" + description + "]";
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Product)) return false;
		
		if(this.id == ((Product)obj).getId())
			return true;
		
		return false;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
