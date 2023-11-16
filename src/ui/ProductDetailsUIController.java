package ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datas.BaseAccess;
import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Product;
import models.ProductImage;

public class ProductDetailsUIController {
	@FXML
	Button deleteButton;
	@FXML
	Button updateButton;
	@FXML
    private Label nameText;
	@FXML
    private Label priceText;
	@FXML
    private Label stockText;
	@FXML
    private Label categoryText;
	@FXML
    private Label descriptionText;
	
    @FXML
    private ImageView productImage1;

    @FXML
    private ImageView productImage2;

    @FXML
    private ImageView productImage3;
    @FXML
    private List<ProductImage> productImages = new ArrayList<>();
	
	private Product product;
	
	public ProductDetailsUIController() {  }
    
    @FXML
    private void initialize() {
    	AppLauncher.getInstance().setProductDetailsUIController(this);
    	displayUserDetails();
    	
    	if((AppLauncher.getInstance().getUserConnectedRole() != Role.CHEF) && 
    			(AppLauncher.getInstance().getUserConnectedRole() != Role.SUPERADMINISTRATOR)) {
    		deleteButton.setVisible(false);
    		updateButton.setVisible(false);
    	}
    }
    
    private void displayUserDetails() {
    	if(product != null) {
    		nameText.textProperty().set(product.getName());
    		priceText.textProperty().set(product.getPrice() + "");
    		stockText.textProperty().set(product.getQuantity() + "");
    		categoryText.textProperty().set(product.getCategory());
    		descriptionText.textProperty().set(product.getDescription());
			updateImages() ;
    	}
    }
    
    private void updateImages() {
		// Chargez les images associées au produit sélectionné
		
		
		if(product != null && !product.getProductImages().isEmpty()) {
			productImages = product.getProductImages();
		}

		// Assurez-vous que les images sont disponibles
		if (!productImages.isEmpty()) {
		    // Chargez les images dans les ImageView
		    Image image1 = new Image(new ByteArrayInputStream(productImages.get(0).getImageData()));
		    productImage1.setImage(image1);

		    if (productImages.size() > 1) {
		        Image image2 = new Image(new ByteArrayInputStream(productImages.get(1).getImageData()));
		        productImage2.setImage(image2);
		    }

		    if (productImages.size() > 2) {
		        Image image3 = new Image(new ByteArrayInputStream(productImages.get(2).getImageData()));
		        productImage3.setImage(image3);
		    }
		}

	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		displayUserDetails();
	}
	
	@FXML
	private void openUpdateProductUI() {
		if(product != null) {
			try {
				AppLauncher.getInstance().handleOpenEditProductUIForUpdate(product);
				closeUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void deleteProduct() {
		if(product != null) {
			BaseAccess.getInstance().deleteProduct(product);
			closeUI();
		}
	}
	
	@FXML
	private void closeUI() {
		Stage stage = (Stage) nameText.getScene().getWindow();
        stage.close();
	}
}
