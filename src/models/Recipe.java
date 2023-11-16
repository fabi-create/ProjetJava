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
import javax.persistence.OneToMany;

@Entity(name="T_Recipes")
public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@OneToMany(mappedBy = "recipe", cascade= {CascadeType.PERSIST})
	private List<Payment> payments = new ArrayList<>();
	
	public Recipe() {}
	
	public Recipe(int id, List<Payment> payments) {
		super();
		this.id = id;
		this.payments = payments;
	}
	
	public Recipe(List<Payment> payments) {
		super();
		this.payments = payments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	public void addPayment(Payment payment) {
		payment.setRecipe(this);
		payments.add(payment);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Recipe)) return false;
		
		if(this.id == ((Recipe)obj).getId())
			return true;
		
		return false;
	}

}
