package ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datas.BaseAccess;
import enums.RecipeFrequency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Payment;
import models.Product;
import models.ProductDetails;
import models.RecipeDetails;
import utils.AppDateParser;
import utils.MonthPeriod;
import utils.WeekPeriod;

public class RecipeUIController {
	
	@FXML
	private ComboBox<RecipeFrequency> frequencyComboBox;
	@FXML
	private ComboBox<String> productComboBox;
	@FXML
	private TableView<RecipeDetails> detailsTableView;
	ObservableList<RecipeDetails> recipesList = FXCollections.observableArrayList();
	@FXML
	private TableColumn<RecipeDetails, String> periodColumn;
	@FXML
	private TableColumn<RecipeDetails, String> recipeColumn;
	
	private RecipeFrequency frequencySelected;
	private Product productSelected;
	
	private List<Payment> paymentsList = new ArrayList<>();
	
	@FXML
	private void initialize() {
		AppLauncher.getInstance().setRecipeUIController(this);
		
		paymentsList = BaseAccess.getInstance().getPayments();
		
		initDetailsTableView();
		initfrequencyComboBox();
		initProductsComboBox();
	}
	
	private void initDetailsTableView() {
		detailsTableView.setItems(recipesList);
		periodColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		recipeColumn.setCellValueFactory(new PropertyValueFactory<>("info"));
	}
	
	private void initfrequencyComboBox() {
		frequencyComboBox.getItems().clear();
		frequencyComboBox.getItems().addAll(RecipeFrequency.values());
		
		// Ajoute un écouteur à la propriété valueProperty du ComboBox
        frequencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler votre méthode ici avec la nouvelle valeur sélectionnée
        	frequencySelected = newValue;
            onComboBoxValueChanged();
        });
        
        // Sélectionnez le premier élément s'il existe
        if (!frequencyComboBox.getItems().isEmpty()) {
            frequencyComboBox.setValue(frequencyComboBox.getItems().get(0));
        }
	}
	
	private void initProductsComboBox() {
		productComboBox.getItems().clear();
		List<Product> products = BaseAccess.getInstance().getProducts();
		productComboBox.getItems().add("0 # Tous");
		for(Product p : products) {
			productComboBox.getItems().add(p.getId() + " # " + p.getName());
		}
		
		// Ajoute un écouteur à la propriété valueProperty du ComboBox
		productComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler votre méthode ici avec la nouvelle valeur sélectionnée
			if(!newValue.equals("0 # Tous")) {
				for(Product p : products) {
					String concat = p.getId() + " # " + p.getName();
					if(concat.equals(newValue)) {
						setProductSelected(p);
					}
				}
			} else {
				setProductSelected(null);
			}
            onComboBoxValueChanged();
        });
		
		// Sélectionnez le premier élément s'il existe
        if (!productComboBox.getItems().isEmpty()) {
        	productComboBox.setValue(productComboBox.getItems().get(0));
        }
	}
	
	// Méthode appelée lorsque la valeur du ComboBox change
    private void onComboBoxValueChanged() {
        // Insérez votre logique ici
    	if (frequencyComboBox != null && frequencyComboBox.getValue() != null) {
    		
    		switch (frequencySelected) {
    		case JOURNALIERE :
    			recipesList.clear();
    			recipesList.addAll(filterAndGroupByDay());
    			break;
    		case HEBDOMADAIRE :
    			recipesList.clear();
    			recipesList.addAll(filterAndGroupByWeek());
    			// recipesList.addAll(updateWeekTotalMap());
    			break;
    		case MENSUELLE :
    			recipesList.clear();
    			// recipesList.addAll(updateMonthTotalMap());
    			recipesList.addAll(filterAndGroupByMonth());
    			break;
    		default :
    			recipesList.clear();
    			recipesList.addAll(filterAndGroupByDay());
    			break;
    		}
    	}
    	initDetailsTableView();
    	detailsTableView.refresh();
    }
	
	private List<RecipeDetails> filterAndGroupByDay() {
		Map<String, Double> periodToTotalMap = new HashMap<>();
		
		for (Payment payment : paymentsList) {
			if (productSelected != null) {
				String paymentPeriod = AppDateParser.getInstance().calculatePeriod(payment.getPaymentDate());
				double[] benefic = {0};
				payment.getOrder().getProductDetails().forEach((p)-> {
					if(p.getProduct().equals(productSelected)) {
						benefic[0] = p.getProduct().getPrice() * p.getQuantity() ;
					}
				});
				double finalBenefic = benefic[0]; // Récupérer la valeur finale
				periodToTotalMap.put(paymentPeriod, periodToTotalMap.getOrDefault(paymentPeriod, 0.0) + finalBenefic);
			} else {
				String period = AppDateParser.getInstance().calculatePeriod(payment.getPaymentDate());
				double benefic = payment.getPayrollAmount() - payment.getRemainder();
				periodToTotalMap.put(period, periodToTotalMap.getOrDefault(period, 0.0) + benefic );
			}
		}

	    return createRecipeDetailsList(periodToTotalMap);
	}
	
	/*
	private boolean isPaymentDateInRange(Date paymentDate, Date startDate, Date endDate) {
	    return !paymentDate.before(startDate) && !paymentDate.after(endDate);
	}
	
	private String getFormattedPeriod(Date date, String pattern) {
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    return sdf.format(date);
	}
	*/
	
	private double calculateBeneficForProduct(Payment pay) {
	    double benefic = 0;
	    
	    if (productSelected != null) {
	        for (ProductDetails p : pay.getOrder().getProductDetails()) {
	            if (p.getProduct().equals(productSelected)) {
	                benefic += p.getProduct().getPrice() * p.getQuantity();
	            }
	        }
	    } else {
			benefic = pay.getPayrollAmount() - pay.getRemainder();
	    }
	    
	    return benefic;
	}
	
	private List<RecipeDetails> createRecipeDetailsList(Map<String, Double> periodToTotalMap) {
	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();
	    
	    for (Map.Entry<String, Double> entry : periodToTotalMap.entrySet()) {
	    	 if (entry.getValue() > 0.0) { // Vérifier si le total est supérieur à zéro
	             RecipeDetails details = new RecipeDetails();
	             details.setItem(entry.getKey());
	             details.setInfo(String.valueOf(entry.getValue()));
	             recipeDetailsList.add(details);
	         }
	    }
	    
	    return recipeDetailsList;
	}
	
	/*
	private List<RecipeDetails> createRecipeDetailsList_(Map<String, Double> periodToTotalMap) {
	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();
	    
	    for (Map.Entry<String, Double> entry : periodToTotalMap.entrySet()) {
	    	 if (entry.getValue() > 0.0) { // Vérifier si le total est supérieur à zéro
	             RecipeDetails details = new RecipeDetails();
	             details.setItem(entry.getKey());
	             details.setInfo(String.valueOf(entry.getValue()));
	             recipeDetailsList.add(details);
	         }
	    }
	    
	    return recipeDetailsList;
	}
	*/
	
	private List<RecipeDetails> updateWeekTotalMap() {
	    Map<Integer, WeekPeriod> periodToTotalMap = new HashMap<>();
	    for (Payment payment : paymentsList) {
	        int paymentWeek = AppDateParser.getInstance().getWeekFromDate(payment.getPaymentDate());
	        LocalDate paymentDate = AppDateParser.getInstance().getDate_FromString(payment.getPaymentDate());
	        
	        LocalDate startOfWeek = paymentDate.with(DayOfWeek.MONDAY);
	        // int weekNumber = startOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
	        int weekNumber = startOfWeek.get(WeekFields.ISO.weekOfWeekBasedYear());
	        LocalDate endOfWeek = startOfWeek.plusDays(6); 
	        
	        
	        // Check if there's an existing entry for the week
	        WeekPeriod existingWeek = periodToTotalMap.get(weekNumber);
	        double benefic = calculateBeneficForProduct(payment);
	        if (existingWeek != null) {
	        	if(benefic > 0){
	        		existingWeek.setTotal(existingWeek.getTotal() + benefic);
	        	}
	            existingWeek.updateDateRange(startOfWeek, endOfWeek);
	        } else {
	        	if(benefic > 0){
	        		periodToTotalMap.put(weekNumber, new WeekPeriod(benefic, startOfWeek, endOfWeek));
	        	}
	        }
	    }
	    
	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();
	    
	    for (Map.Entry<Integer, WeekPeriod> entry : periodToTotalMap.entrySet()) {
	    	 if (entry.getValue().getTotal() > 0.0) { // Vérifier si le total est supérieur à zéro
	             RecipeDetails details = new RecipeDetails();
	             details.setItem(entry.getValue().getFormattedPeriod());
	             details.setInfo(String.valueOf(entry.getValue().getTotal()));
	             recipeDetailsList.add(details);
	         }
	    }
	    
	    return recipeDetailsList;
	}
	
	private List<RecipeDetails> updateMonthTotalMap() {
	    Map<YearMonth, MonthPeriod> periodToTotalMap = new HashMap<>();
	    for (Payment payment : paymentsList) {
	        LocalDate paymentDate = AppDateParser.getInstance().getDate_FromString(payment.getPaymentDate());
	        YearMonth paymentYearMonth = YearMonth.from(paymentDate);

	        // Check if there's an existing entry for the month
	        MonthPeriod existingMonth = periodToTotalMap.get(paymentYearMonth);
	        double benefic = calculateBeneficForProduct(payment);
	        if (existingMonth != null) {
	            if (benefic > 0) {
	                existingMonth.setTotal(existingMonth.getTotal() + benefic);
	            }
	        } else {
	            if (benefic > 0) {
	                periodToTotalMap.put(paymentYearMonth, new MonthPeriod(benefic, paymentYearMonth));
	            }
	        }
	    }

	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();

	    for (Map.Entry<YearMonth, MonthPeriod> entry : periodToTotalMap.entrySet()) {
	        if (entry.getValue().getTotal() > 0.0) {
	            RecipeDetails details = new RecipeDetails();
	            details.setItem(entry.getValue().getFormattedPeriod());
	            details.setInfo(String.valueOf(entry.getValue().getTotal()));
	            recipeDetailsList.add(details);
	        }
	    }

	    return recipeDetailsList;
	}

	private List<RecipeDetails> filterAndGroupByWeek() {
		Map<Integer, WeekPeriod> periodToTotalMap = new HashMap<>();
	    for (Payment payment : paymentsList) {
	        // int paymentWeek = AppDateParser.getInstance().getWeekFromDate(payment.getPaymentDate());
	        LocalDate paymentDate = AppDateParser.getInstance().getDate_FromString(payment.getPaymentDate());
	        
	        LocalDate startOfWeek = paymentDate.with(DayOfWeek.MONDAY);
	        // int weekNumber = startOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
	        int weekNumber = startOfWeek.get(WeekFields.ISO.weekOfWeekBasedYear());
	        LocalDate endOfWeek = startOfWeek.plusDays(6); 
	        
	        
	        // Check if there's an existing entry for the week
	        WeekPeriod existingWeek = periodToTotalMap.get(weekNumber);
	        double benefic = calculateBeneficForProduct(payment);
	        if (existingWeek != null) {
	        	if(benefic > 0){
	        		existingWeek.setTotal(existingWeek.getTotal() + benefic);
	        	}
	            existingWeek.updateDateRange(startOfWeek, endOfWeek);
	        } else {
	        	if(benefic > 0){
	        		periodToTotalMap.put(weekNumber, new WeekPeriod(benefic, startOfWeek, endOfWeek));
	        	}
	        }
	    }
	    
	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();
	    
	    for (Map.Entry<Integer, WeekPeriod> entry : periodToTotalMap.entrySet()) {
	    	 if (entry.getValue().getTotal() > 0.0) { // Vérifier si le total est supérieur à zéro
	             RecipeDetails details = new RecipeDetails();
	             details.setItem(entry.getValue().getFormattedPeriod());
	             details.setInfo(String.valueOf(entry.getValue().getTotal()));
	             recipeDetailsList.add(details);
	         }
	    }
	    
	    return recipeDetailsList;
	}

	private List<RecipeDetails> filterAndGroupByMonth() {
		Map<YearMonth, MonthPeriod> periodToTotalMap = new HashMap<>();
	    for (Payment payment : paymentsList) {
	        LocalDate paymentDate = AppDateParser.getInstance().getDate_FromString(payment.getPaymentDate());
	        YearMonth paymentYearMonth = YearMonth.from(paymentDate);

	        // Check if there's an existing entry for the month
	        MonthPeriod existingMonth = periodToTotalMap.get(paymentYearMonth);
	        double benefic = calculateBeneficForProduct(payment);
	        if (existingMonth != null) {
	            if (benefic > 0) {
	                existingMonth.setTotal(existingMonth.getTotal() + benefic);
	            }
	        } else {
	            if (benefic > 0) {
	                periodToTotalMap.put(paymentYearMonth, new MonthPeriod(benefic, paymentYearMonth));
	            }
	        }
	    }

	    List<RecipeDetails> recipeDetailsList = new ArrayList<>();

	    for (Map.Entry<YearMonth, MonthPeriod> entry : periodToTotalMap.entrySet()) {
	        if (entry.getValue().getTotal() > 0.0) {
	            RecipeDetails details = new RecipeDetails();
	            details.setItem(entry.getValue().getFormattedPeriod());
	            details.setInfo(String.valueOf(entry.getValue().getTotal()));
	            recipeDetailsList.add(details);
	        }
	    }

	    return recipeDetailsList;
	}


    @FXML
	private void quit() {
		Stage stage = (Stage) frequencyComboBox.getScene().getWindow();
        stage.close();
	}

	public Product getProductSelected() {
		return productSelected;
	}

	public void setProductSelected(Product productSelected) {
		this.productSelected = productSelected;
	}
	
	public RecipeFrequency getFrequencySelected() {
		return frequencySelected;
	}

	public void setFrequencySelected(RecipeFrequency frequencySelected) {
		this.frequencySelected = frequencySelected;
	}

	public List<Payment> getPaymentsList() {
		return paymentsList;
	}

	public void setPaymentsList(List<Payment> paymentsList) {
		this.paymentsList = paymentsList;
	}

}
