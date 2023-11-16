package ui;

import java.io.IOException;

import datas.BaseAccess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Order;
import models.Payment;

public class PaymentUIController {
	
	@FXML
	private Label orderReferenceLabel;
	@FXML
	private Label orderDateLabel;
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Label balanceDueLabel;
	@FXML
	private Label paymentReferenceLabel;
	@FXML
	private Label paymentDateLabel;
	@FXML
	private TextField payrollAmountTextField;
	@FXML
	private TextField remainderTextField;
	
	private double balanceDue = 0;
	
	Order order = null;
	Payment payment = null;

	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setPaymentUIController(this);
    	
    	initInformations();
    }
	
	private void initInformations() {
		remainderTextField.textProperty().set("0");
		if(order != null) {
			orderReferenceLabel.textProperty().set(order.getOrderReference());
			orderDateLabel.textProperty().set(order.getHourOrder());
			totalPriceLabel.textProperty().set(order.getTotalPrice() + "");
			
			if(order.getPayment() != null) {
				payment = order.getPayment();
				
				payrollAmountTextField.textProperty().set(order.getPayment().getPayrollAmount() + "");
				remainderTextField.textProperty().set(order.getPayment().getRemainder() + "");
				
				paymentReferenceLabel.textProperty().set(order.getPayment().getPaymentReference());
				paymentDateLabel.textProperty().set(order.getPayment().getPaymentDate());
			}
		}
		
		// Ajouter un ChangeListener à TextField
		payrollAmountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Cette méthode sera appelée chaque fois que le contenu de TextField change
                // System.out.println("Nouvelle valeur : " + newValue);
                // Appeler votre méthode ici
                calculBalanceDue(newValue);
            }
        });
	}

	public PaymentUIController() {}
	
	@FXML
	private void calculBalanceDue(String value) {
		balanceDue = Double.parseDouble(value) - order.getTotalPrice();
		balanceDueLabel.textProperty().set(balanceDue + "");
	}
	
	@FXML
	private void deletePayment() {
		if(order.getPayment() != null) {
			payment = order.getPayment();
			order.setPayment(null); // Dissocier la commande du paiement
			BaseAccess.getInstance().updateOrder(order);
			
			if(BaseAccess.getInstance().deletePayment(payment) != null) {
				DialogHelper.showInformation("Information", "Suppression de paiement réussie !");
				quit();
			} else {
				DialogHelper.showError("Erreur", "Echec de suppression du paiement !");
			}
		} else {
			DialogHelper.showError("Erreur", "Le paiement n'existe pas car vous ne l'avez pas encore validé !");		
		}
	}
	
	@FXML
	private void validPayment() {
		if(remainderTextField.textProperty().get().isEmpty()) {
			remainderTextField.textProperty().set("0");
		}
		
		if(order != null) {
			if(payrollAmountTextField.textProperty().get().isEmpty()) {
				DialogHelper.showError("Erreur", "Votre champ du montant payé est vide !");
			} else {
				if(order.getTotalPrice() > Double.parseDouble(payrollAmountTextField.textProperty().get())) {
					DialogHelper.showError("Erreur", "Le prix de la commande est supérieur au montant de paiement. \n" + 
							"Vérifiez le montant de paiement s'il vous plaît !!!");
				} else {
					if(balanceDue > Double.parseDouble(remainderTextField.textProperty().get())) {
						DialogHelper.showWarning("Erreur", "Le montant rendu est inférieur au montant dû. \n" + 
								"Vérifiez le montant de paiement et celui rendu s'il vous plaît !!!");
					} else if(balanceDue < Double.parseDouble(remainderTextField.textProperty().get())) {
						DialogHelper.showWarning("Attention", "Le montant rendu est supérieur au montant dû !");
					} 
					
					// Pour une mise à jour car le paiement existe déjà
					if(order.getPayment() != null) {
						// payment.setOrder(order);
						payment.setPayrollAmount(Double.parseDouble(payrollAmountTextField.textProperty().get()));
						payment.setRemainder(Double.parseDouble(remainderTextField.textProperty().get()));
						//
						//
						//
						payment.updateDate();
						
						// order.setPayment(payment);
						
						if(BaseAccess.getInstance().updatePayment(payment) != null) {
							DialogHelper.showInformation("Information", "Paiement réussi !");
							quit();
						} else {
							DialogHelper.showError("Erreur", "Echec de paiement !");
						}
					} 
					// Pour une création de paiement
					else {
						// Paiement peut importe le montant rendu
						payment = new Payment();
						payment.setOrder(order);
						payment.setPayrollAmount(Double.parseDouble(payrollAmountTextField.textProperty().get()));
						payment.setRemainder(Double.parseDouble(remainderTextField.textProperty().get()));
						
						order.setPayment(payment);
						
						if(BaseAccess.getInstance().addPayment(payment) != null) {
							DialogHelper.showInformation("Information", "Paiement réussi !");
							quit();
							
							/*
							try {
								AppLauncher.getInstance().handleOpenOrdersUI();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							*/
						} else {
							DialogHelper.showError("Erreur", "Echec de paiement !");
						}
					}
				}
			}
		}
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Order getOrder() {
		return order;
	}
	
	@FXML
	private void quit() {
		Stage stage = (Stage) payrollAmountTextField.getScene().getWindow();
        stage.close();
	}
	
	public void setOrder(Order order) {
		this.order = order;
		initInformations();
	}
}
