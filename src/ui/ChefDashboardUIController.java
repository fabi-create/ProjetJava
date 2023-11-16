package ui;

import java.io.IOException;

import datas.BaseAccess;
import enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Product;

public class ChefDashboardUIController {
	
	@FXML
	private Button quitButton;

	@FXML
	private TextField searchTextField;
	
	@FXML
	private TableView<Product> productsTableView;
	@FXML
	private TableColumn<Product, String> idColumn;
	@FXML
	private TableColumn<Product, String> nameColumn;
	@FXML
	private TableColumn<Product, String> priceColumn;
	@FXML
	private TableColumn<Product, String> quantityColumn;
	@FXML
	private TableColumn<Product, String> categoryColumn;
	
	private Product productSelected;
	
	ObservableList<Product> productsList = FXCollections.observableArrayList();
	private FilteredList<Product> filteredProducts;
	
	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setChefDashUIController(this);
    	
    	initProductsTableView();
    	
    	if(AppLauncher.getInstance().getUserConnectedRole() == Role.SUPERADMINISTRATOR) {
    		quitButton.setVisible(false);
    	}
    	
		initFilteredList();
    }
	
	private void initProductsTableView() {
		productsList.clear();
		productsList.addAll(BaseAccess.getInstance().getProducts());
		// TableView
		productsTableView.setItems(productsList);
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    	quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    	categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    	// typeColumn.setCellFactory(column -> new UserTypeTableCell());
    	
    	productsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    	    // Gérer l'événement de sélection ici
    	    if (newValue != null) {
    	        // Vous pouvez accéder aux propriétés de l'utilisateur sélectionné
    	        
    	        // Faites ce que vous voulez avec les informations de l'utilisateur sélectionné
    	    	productSelected = newValue;
    	    }
    	});
	}
	
	private void initFilteredList() {
		// Créez le FilteredList et associez-le à la liste complète de paiements
	    filteredProducts = new FilteredList<>(productsList, p -> true);
	    
	    // Liez le FilteredList à la TableView
	    productsTableView.setItems(filteredProducts);
	    
	 // Écoutez les changements dans le champ de recherche et filtrez les résultats en conséquence
	    searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredProducts.setPredicate(pdt -> {
	            // Si le champ de recherche est vide, affichez tous les paiements
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	            
	            // Vérifiez si la valeur de recherche est contenue dans une colonne
	            String lowerCaseFilter = newValue.toLowerCase();
	            if (pdt.getName().toLowerCase().contains(lowerCaseFilter) ||
	                pdt.getCategory().toLowerCase().contains(lowerCaseFilter) ||
	                Integer.toString(pdt.getId()).toLowerCase().contains(lowerCaseFilter)||
	                Integer.toString(pdt.getQuantity()).toLowerCase().contains(lowerCaseFilter)||
	                Double.toString(pdt.getPrice()).toLowerCase().contains(lowerCaseFilter)||
	                pdt.getDescription().toLowerCase().contains(lowerCaseFilter)
	                // Ajoutez d'autres colonnes ici...
	            ) {
	                return true; // Correspondance trouvée
	            }
	            
	            return false; // Aucune correspondance trouvée
	        });
	    });
	}
	
	@FXML
	private void openProductDetailsUI() {
		if(productSelected != null) {
			try {
				AppLauncher.getInstance().handleOpenProductDetailsUI(productSelected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un produit d'abord !");
		}
	}
	
	@FXML
	private void openAddProductUI() {
		try {
			AppLauncher.getInstance().handleOpenEditProductUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void openUpdateProductUI() {
		if(productSelected != null) {
			try {
				AppLauncher.getInstance().handleOpenEditProductUIForUpdate(productSelected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un produit d'abord !");
		}
	}
	
	@FXML
	private void deleteProduct() {
		if(productSelected != null) {
			BaseAccess.getInstance().deleteProduct(productSelected);
			initialize();
			productSelected = null;
			DialogHelper.showInformation("Information", "Suppression réussie !");
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un produit d'abord !");
		}
	}
	
	@FXML
	private void logout() {
		Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
		AppLauncher.getInstance().initRootLayout();
	}
}
