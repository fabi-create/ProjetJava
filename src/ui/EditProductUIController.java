package ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import datas.BaseAccess;
import enums.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Product;
import models.ProductImage;

public class EditProductUIController {
	@FXML
	private Button uploadButton;
	@FXML
    private Label errorText;
	@FXML
    private Label successText;
	@FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private ComboBox<Category> categoryComboBox;
    
    @FXML
    private ImageView productImage1;

    @FXML
    private ImageView productImage2;

    @FXML
    private ImageView productImage3;
    
    @FXML
    private List<ProductImage> productImages = new ArrayList<>();
    
    
	Product product;

	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setEditProductUIController(this);
    	categoryComboBox.getItems().clear();
    	categoryComboBox.getItems().addAll(Category.values());
        
        errorText.textProperty().set("");
		successText.setVisible(false);
		
		displayProductDetails();
    }
	public EditProductUIController() { }
	
	private void displayProductDetails() {
		if(product != null) {
			nameTextField.setText(product.getName());
			priceTextField.setText(product.getPrice() + "");
			descriptionTextField.setText(product.getDescription());
			stockTextField.setText(product.getQuantity() + "");
			updateImages() ;
		}
	}
	
	@FXML
	private void validEdition() {
		if(nameTextField.getText().isEmpty() || 
			priceTextField.getText().isEmpty() || 
			descriptionTextField.getText().isEmpty() ||
			stockTextField.getText().isEmpty() || 
			categoryComboBox.getSelectionModel().isEmpty()) {
				errorText.textProperty().set("Les champs ne sont pas valident");
				DialogHelper.showWarning("Attention", "Les champs ne sont pas valident");
		} else {
			String categoryValue = categoryComboBox.getSelectionModel().getSelectedItem().getCategory();
			
			Product productAdded = null;
			Product objUser = new Product(nameTextField.getText(), 
				Double.parseDouble(priceTextField.getText()), 
				descriptionTextField.getText(), 
				Integer.parseInt(stockTextField.getText()),
				categoryValue
			);
			for(ProductImage i : productImages) {
				i.setProduct(objUser);
				objUser.addProductImage(i);
			}
			
			objUser.setProductImages(productImages);
			// Pour l'ajout
			if(product == null) {
				productAdded = BaseAccess.getInstance().addProduct(objUser);
			}  else {
				// Pour la mise à jour 
				objUser.setId(product.getId());
				productAdded = BaseAccess.getInstance().updateProduct(objUser);
			}
			
			if(productAdded != null) {
				successText.setVisible(true);
				DialogHelper.showInformation("Information", successText.getText());
				clearInputs();
			} else {
				successText.setVisible(false);
			}
		}
	}
	
	@FXML
	private void uploadProductImage() {
	    FileChooser fileChooser = new FileChooser();
	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.jpeg");
	    fileChooser.getExtensionFilters().add(extFilter);

	    File selectedFile = fileChooser.showOpenDialog((Stage)uploadButton.getScene().getWindow());
	    if (selectedFile != null) {
	        try {
	            byte[] imageData = Files.readAllBytes(selectedFile.toPath());
	            
	            // Créer un nouvel objet ProductImage
	            ProductImage productImage = new ProductImage(imageData, product);
	            productImages.add(productImage);
	            // Ajouter l'image au produit
	            /*
	            if(product != null) {
	            	product.addProductImage(productImage);
	            }
	            */
	            
	            // Enregistrer l'image dans la base de données
	            // saveProductImageToDatabase(productImage);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    updateImages();
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
		} else {
		    // Effacez les ImageView si aucune image n'est disponible
			 clearImages();
		}

	}
	
	@FXML
	private void clearImages() {
		productImages.clear();
		productImage1.setImage(null);
	    productImage2.setImage(null);
	    productImage3.setImage(null);
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		displayProductDetails();
	}
	
	@FXML
	private void clearInputs() {
		nameTextField.clear();
		priceTextField.clear();
		descriptionTextField.clear();
		stockTextField.clear();
		errorText.textProperty().set(".");
		clearImages();
		product = null;
	}
	
	@FXML
	private void closeUI() {
		Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
	}

}
