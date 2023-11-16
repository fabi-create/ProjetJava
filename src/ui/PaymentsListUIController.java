package ui;

import datas.BaseAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import master.AppLauncher;
import models.Payment;

public class PaymentsListUIController {
	
	@FXML
	private TextField searchTextField;
	
	@FXML
	private TableView<Payment> paymentsTableView;
	ObservableList<Payment> paymentsList = FXCollections.observableArrayList();
	private FilteredList<Payment> filteredPayments;

	
	@FXML
	private TableColumn<Payment, String> idColumn;
	@FXML
	private TableColumn<Payment, String> paymentReferenceColumn;
	@FXML
	private TableColumn<Payment, String> paymentDateColumn;
	@FXML
	private TableColumn<Payment, Double> remainderColumn;
	@FXML
	private TableColumn<Payment, Double> payrollAmountColumn;
	@FXML
	private TableColumn<Payment, String> orderReferenceColumn;
	
	@FXML
	private void initialize() {
		AppLauncher.getInstance().setPaymentsListUIController(this);
		
		initPaymentsTableView();
		
		initFilteredList();
	}

	private void initPaymentsTableView() {
		paymentsList.addAll(BaseAccess.getInstance().getPayments());
		paymentsTableView.setItems(paymentsList);
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		paymentReferenceColumn.setCellValueFactory(new PropertyValueFactory<>("paymentReference"));
		paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
		remainderColumn.setCellValueFactory(new PropertyValueFactory<>("remainder"));
		payrollAmountColumn.setCellValueFactory(new PropertyValueFactory<>("payrollAmount"));

		orderReferenceColumn.setCellValueFactory(data -> 
			new SimpleStringProperty(data.getValue().getOrder().getOrderReference()));
	}
	
	private void initFilteredList() {
		// Créez le FilteredList et associez-le à la liste complète de paiements
	    filteredPayments = new FilteredList<>(paymentsList, p -> true);
	    
	    // Liez le FilteredList à la TableView
	    paymentsTableView.setItems(filteredPayments);
	    
	 // Écoutez les changements dans le champ de recherche et filtrez les résultats en conséquence
	    searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredPayments.setPredicate(payment -> {
	            // Si le champ de recherche est vide, affichez tous les paiements
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	            
	            // Vérifiez si la valeur de recherche est contenue dans une colonne
	            String lowerCaseFilter = newValue.toLowerCase();
	            if (payment.getPaymentReference().toLowerCase().contains(lowerCaseFilter) ||
	                payment.getPaymentDate().toLowerCase().contains(lowerCaseFilter) ||
	                Double.toString(payment.getPayrollAmount()).toLowerCase().contains(lowerCaseFilter)||
	                Double.toString(payment.getRemainder()).toLowerCase().contains(lowerCaseFilter)||
	                payment.getOrder().getOrderReference().toLowerCase().contains(lowerCaseFilter)
	                // Ajoutez d'autres colonnes ici...
	            ) {
	                return true; // Correspondance trouvée
	            }
	            
	            return false; // Aucune correspondance trouvée
	        });
	    });
	}

}
