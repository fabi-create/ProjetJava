package ui;

import java.io.IOException;
import java.util.List;

import datas.BaseAccess;
import enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Payment;
import models.Product;

public class RestaurateurUIController {
	
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
	
	@FXML
	private Label counterProductSelected;
	
	private Product productSelected;
	
	ObservableList<Product> productsList = FXCollections.observableArrayList();
	ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
	private FilteredList<Product> filteredProducts;
	
	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setRestaurateurUIController(this);
    	
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
    	
    	productsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	productsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    	    // Gérer l'événement de sélection ici
    	    if (newValue != null) {
    	        // Vous pouvez accéder aux propriétés de l'utilisateur sélectionné
    	        
    	        // Faites ce que vous voulez avec les informations de l'utilisateur sélectionné
    	    	productSelected = newValue;
    	    	// openProductDetailsUI();
    	    	updateRowsSelectedCounter();
    	    }
    	});
	}
	
	private void updateRowsSelectedCounter() {
		selectedProducts = productsTableView.getSelectionModel().getSelectedItems();
		counterProductSelected.textProperty().set(selectedProducts.size() + "");
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
	private void openCreateOrderUI() {
		if(selectedProducts.isEmpty()) {
			DialogHelper.showWarning("Attention", "Sélectionnez au moins un article d'abord !");
		} else {
			try {
				// List<Product> pList = (List<Product>) selectedProducts.iterator();
				AppLauncher.getInstance().handleOpenEditOrdersUI(selectedProducts);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	private void openOrdersUI() {
		try {
			AppLauncher.getInstance().handleOpenOrdersUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void openPaymentsUI() {
		try {
			AppLauncher.getInstance().handleOpenPaymentsListUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void openRecipesUI() {
		try {
			AppLauncher.getInstance().handleOpenRecipeUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public RestaurateurUIController() { }
	
	@FXML
	private void logout() {
		Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
		AppLauncher.getInstance().initRootLayout();
	}
}
