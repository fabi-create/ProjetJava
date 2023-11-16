package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "T_ProductImages")
public class ProductImage implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

	@Lob
    @Column(name = "image_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() { }

    public ProductImage(byte[] imageData, Product product) {
        this.imageData = imageData;
        this.product = product;
    }
    
    public ProductImage(byte[] imageData) {
        this.imageData = imageData;
    }

    // Getter and Setter methods

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
