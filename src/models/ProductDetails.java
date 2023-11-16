package models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="T_ProductDetails")
public class ProductDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private int quantity;
    private double total;

    public ProductDetails() {
    	
    }
    
    public ProductDetails(Product product, int quantity) {
        this.setProduct(product);
        this.setQuantity(quantity);
        this.setTotal(product.getPrice() * quantity);
    }
    
    public ProductDetails(Product product, Order order, int quantity) {
        this.setProduct(product);
        this.setOrder(order);
        this.setQuantity(quantity);
        this.setTotal(product.getPrice() * quantity);
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		updateTotal();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void updateTotal() {
		this.setTotal(this.quantity * this.product.getPrice());
	}
	
	// Implémentation de equals() et hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetails that = (ProductDetails) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
