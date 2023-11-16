package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datas.BaseAccess;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Order;
import models.Product;
import models.ProductDetails;
import models.User;

public class OrdersUIController {
	
	@FXML
	private TextField searchTextField;
	@FXML
	private Button addOrder;
	@FXML
    private TableView<Order> ordersTableView;
	@FXML
	private TableColumn<User, String> idColumn;
	@FXML
	private TableColumn<User, String> referenceColumn;
	@FXML
	private TableColumn<User, String> dateColumn;
	@FXML
    private TableView<ProductDetails> productsTableView;
	@FXML
	private TableColumn<ProductDetails, Integer> productIdColumn;
	@FXML
	private TableColumn<ProductDetails, String> productNameColumn;
	@FXML
	private TableColumn<ProductDetails, Integer> productQuantityColumn;
	@FXML
	private TableColumn<ProductDetails, Double> productUnitPriceColumn;
	@FXML
	private TableColumn<ProductDetails, Double> productTotalColumn;
	
	
	
	@FXML
	private Label orderDateLabel;
	@FXML
	private Label orderReferenceLabel;
	@FXML
	private Label orderTotalPriceLabel;
	@FXML
	private Label orderPaiementStatusLabel;
	
	private Order orderSelected;
	
	ObservableList<Order> ordersList = FXCollections.observableArrayList();
	ObservableList<ProductDetails> productsList = FXCollections.observableArrayList();
	

	private FilteredList<Order> filteredOrders;
	
	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setOrdersUIController(this);
    	
    	initOrdersTableView();
    	
    	initFilteredList();
    }
	
	private void initOrdersTableView() {
		
		ordersList.clear();
		ordersList.addAll(BaseAccess.getInstance().getOrders());
		// TableView
		ordersTableView.setItems(ordersList);
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	referenceColumn.setCellValueFactory(new PropertyValueFactory<>("orderReference"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("hourOrder"));
    	
    	ordersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    	    // Gérer l'événement de sélection ici
    	    if (newValue != null) {
    	        // Vous pouvez accéder aux propriétés de l'utilisateur sélectionné
    	        
    	        // Faites ce que vous voulez avec les informations de l'utilisateur sélectionné
    	    	orderSelected = newValue;
    	    	updateProductDetails();
    	    }
    	});
    	
	}
	
	public OrdersUIController() { }
	
	private void updateProductDetails() {
		if(orderSelected != null) {
			
			orderDateLabel.textProperty().set(orderSelected.getHourOrder());
			orderReferenceLabel.textProperty().set(orderSelected.getOrderReference());
			
			
			if(orderSelected.getPayment() == null) {
				orderPaiementStatusLabel.textProperty().set("Non Payé");
			} else {
				String paymentStatus = "Le " + orderSelected.getPayment().getPaymentDate() + "\n" +
						"Somme de " + orderSelected.getPayment().getPayrollAmount()  + "XOF \n" +
						"Relicat de " + orderSelected.getPayment().getRemainder() + "XOF \n";
				orderPaiementStatusLabel.textProperty().set("Payé " + paymentStatus);
			}
			
			//ordersList.clear();
			if(!orderSelected.getProductDetails().isEmpty()) {
				/*
				List<Product> pList = BaseAccess.getInstance().getProducts();
				// productsList.addAll(BaseAccess.getInstance().getProducts());
				// productsTableView.setItems(productsList);
				
				for(Product p : orderSelected.getProducts()) {
					if(!productsList.contains(p)) {
						productsList.add(p);
					}
				}
				*/
				
				List<ProductDetails> productDetailsList = new ArrayList<>();
				for (Product p : orderSelected.getProducts()) {
				    int quantity = Collections.frequency(orderSelected.getProducts(), p);
				    ProductDetails productDetails = new ProductDetails(p, quantity);

				    if (!productDetailsList.contains(productDetails)) {
				        productDetailsList.add(productDetails);
				    }
				}
				
				productsList.clear();
				productsList.addAll(orderSelected.getProductDetails());
				productsTableView.setItems(productsList);
				
				productIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProduct().getId()).asObject());
				productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
				// productUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
				
				productQuantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
				productUnitPriceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduct().getPrice()).asObject());
				productTotalColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotal()).asObject());
				
				orderTotalPriceLabel.textProperty().set(orderSelected.getTotalPrice() + "");
			}
		} 
	}
	
	private void initFilteredList() {
		// Créez le FilteredList et associez-le à la liste complète de paiements
	    filteredOrders = new FilteredList<>(ordersList, p -> true);
	    
	    // Liez le FilteredList à la TableView
	    ordersTableView.setItems(filteredOrders);
	    
	 // Écoutez les changements dans le champ de recherche et filtrez les résultats en conséquence
	    searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredOrders.setPredicate(od -> {
	            // Si le champ de recherche est vide, affichez tous les paiements
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	            
	            // Vérifiez si la valeur de recherche est contenue dans une colonne
	            String lowerCaseFilter = newValue.toLowerCase();
	            if (od.getOrderReference().toLowerCase().contains(lowerCaseFilter) ||
	                od.getHourOrder().toLowerCase().contains(lowerCaseFilter)
	                // Ajoutez d'autres colonnes ici...
	            ) {
	                return true; // Correspondance trouvée
	            }
	            
	            return false; // Aucune correspondance trouvée
	        });
	    });
	}
	
	
	@FXML
	private void goToEditPayment() {
		if(orderSelected != null) {
			try {
				AppLauncher.getInstance().handleOpenPaymentUI(orderSelected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DialogHelper.showError("Erreur", "Aucune commande n'a été sélectionné !");
		}
	}
	
	@FXML
	private void goToEditOrder() {
		quit();
		DialogHelper.showInformation("Information", 
				"Sélectionnez les produits dans le catalogue afin de créer une commande (CTRL + Ok) !");
	}
	
	@FXML
	private void deleteOrder() {
		if(orderSelected != null) {
			for (ProductDetails details : orderSelected.getProductDetails()) {
		        Product pItem = details.getProduct();
		        int quantityRest = pItem.getQuantity() + details.getQuantity();
		        pItem.setQuantity(quantityRest);
		        details.getProduct().setQuantity(quantityRest);
		        
		        BaseAccess.getInstance().updateProduct(pItem);
		    }
			
			Order o = BaseAccess.getInstance().deleteOrder(orderSelected);
			if(o != null) {
				DialogHelper.showInformation("Information", "Commande supprimée avec succès !");
				initOrdersTableView();
				orderSelected = null;
				updateProductDetails();
				ordersTableView.refresh();
				productsTableView.getItems().clear();
				productsTableView.refresh();
			} else {
				DialogHelper.showError("Erreur", "Erreur lors de la supression de la commande !");
			}
		} else {
			DialogHelper.showError("Erreur", "Aucune commande n'a été sélectionner !");
		}
	}
	
	@FXML
	public void quit() {
		Stage stage = (Stage) ordersTableView.getScene().getWindow();
        stage.close();
	}
}
