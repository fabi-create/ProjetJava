package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datas.BaseAccess;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javassist.tools.Callback;
import master.AppLauncher;
import models.Order;
import models.Product;
import models.ProductDetails;

public class CreateOrderUIController {
	
	private List<Product> products = new ArrayList<>();

	private List<ProductDetails> productDetailsList = new ArrayList<>();
	
	@FXML
	private Label totalPriceLabel;
	
	@FXML
	TableView<ProductDetails> productsTableView;
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
	
	private ObservableList<ProductDetails> productQuantityList = FXCollections.observableArrayList();
	
	private Order orderCreated;

	
	@FXML
	private void initialize() {
		AppLauncher.getInstance().setCreateOrderUIController(this);
		initProductsDetails();
		updateTableView();
	}
	
	private void initProductsDetails() {
		productDetailsList.clear();
		productQuantityList.clear();
		for (Product p : products) {
		    int quantity = Collections.frequency(products, p);
		    ProductDetails productDetails = new ProductDetails(p, quantity);

		    if (!productDetailsList.contains(productDetails)) {
		        productDetailsList.add(productDetails);
		    }
		    
		    if (!productQuantityList.contains(productDetails)) {
		    	productQuantityList.add(productDetails);
		    }
		}
	}
	
	private void updateTableView() {
		initProductsDetails();
		// productsTableView.setItems(productQuantityList);
		productIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProduct().getId()).asObject());
		productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
		productUnitPriceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduct().getPrice()).asObject());
		productTotalColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotal()).asObject());
		
		/*
		productQuantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
		// productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productQuantityColumn.setCellFactory(column -> new QuantityEditingCell());
		productQuantityColumn.setOnEditCommit(event -> {
            ProductDetails product = event.getTableView().getItems().get(event.getTablePosition().getRow());
            product.setQuantity(event.getNewValue());
        });
		*/
		
		//productQuantityColumn.setCellFactory(column -> new IncrementCell());
		
		productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productQuantityColumn.setCellFactory(column -> new TableCell<ProductDetails, Integer>() {
			private final HBox buttonBox = new HBox(); // Conteneur pour les boutons
		    private final Button decrementButton = new Button("-");
		    private final Button incrementButton = new Button("+");
		    
		    {
		        incrementButton.setOnAction(event -> {
		            if (!isEmpty()) {
		                ProductDetails productDetails = getTableView().getItems().get(getIndex());
		                int currentQuantity = productDetails.getQuantity();
		                if(productDetails.getProduct().getQuantity() > currentQuantity) {
		                	productDetails.setQuantity(currentQuantity + 1);
		                	updateItem(currentQuantity + 1, false);
		                	updateTotalPrice();
		                }
		            }
		        });
		        
		        decrementButton.setOnAction(event -> {
		            if (!isEmpty()) {
		                ProductDetails productDetails = getTableView().getItems().get(getIndex());
		                int currentQuantity = productDetails.getQuantity();
		                if(currentQuantity > 1) {
		                	productDetails.setQuantity(currentQuantity - 1);
		                	updateItem(currentQuantity - 1, false);
		                	updateTotalPrice();
		                }
		            }
		        });// Ajouter les boutons au conteneur
		        buttonBox.getChildren().addAll(incrementButton, decrementButton);
		    }
		    
		    @Override
		    protected void updateItem(Integer item, boolean empty) {
		        super.updateItem(item, empty);
		        
		        if (empty) {
		            setGraphic(null);
		        } else {
		            setText(item.toString());
		            setGraphic(buttonBox);
		        }
		    }
		});

        // table.getColumns().add(SpinnerCol);
		
		productsTableView.getItems().addAll(productQuantityList);
		updateTotalPrice();

	}
	
	public CreateOrderUIController() {}
	
	private double calculTotalPrice() {
		double total = 0;
		for (ProductDetails p : productQuantityList) {
		    total += p.getTotal();
		}
		return total;
	}
	
	private void updateTotalPrice() {
		totalPriceLabel.textProperty().set(calculTotalPrice() + "");
		
		// Parcourir les éléments de la TableView et recalculer les totaux
	    for (ProductDetails productDetails : productsTableView.getItems()) {
	        double newTotal = productDetails.getProduct().getPrice() * productDetails.getQuantity();
	        productDetails.setTotal(newTotal);
	    }
	    productsTableView.refresh();
	}
	
	@FXML
	private void validOrder() {
		orderCreated = new Order();
		orderCreated.setProductDetails(productDetailsList);
		orderCreated.setProducts(products);
		orderCreated.setTotalPrice(calculTotalPrice());
		
		for (ProductDetails details : productDetailsList) {
	        Product pItem = details.getProduct();
	        int quantityRest = pItem.getQuantity() - details.getQuantity();
	        pItem.setQuantity(quantityRest);
	        details.getProduct().setQuantity(quantityRest);
	        
	        BaseAccess.getInstance().updateProduct(pItem);
	    }
		
		Order o = BaseAccess.getInstance().addOrder(orderCreated);
		if(o != null) {
			DialogHelper.showInformation("Information", "Commande ajouté avec succès !");
			quit();
		} else {
			DialogHelper.showError("Erreur", "Erreur lors de la création de la commande !");
		}
		
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
		initProductsDetails();
		updateTableView();
	}

	public List<ProductDetails> getProductDetailsList() {
		return productDetailsList;
	}

	public void setProductDetailsList(List<ProductDetails> productDetails) {
		this.productDetailsList = productDetails;
	}

	public Order getOrderCreated() {
		return orderCreated;
	}

	public void setOrderCreated(Order orderCreated) {
		this.orderCreated = orderCreated;
	}
	
	@FXML
	private void quit() {
		Stage stage = (Stage) productsTableView.getScene().getWindow();
        stage.close();
	}

}
