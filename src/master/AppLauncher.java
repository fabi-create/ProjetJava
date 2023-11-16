//package master;
//
//import java.io.IOException;
//import java.util.List;
//
//import enums.Role;
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import models.Order;
//import models.Product;
//import models.User;
//import ui.AdminDashboardUIController;
//import ui.AuthUIController;
//import ui.ChefDashboardUIController;
//import ui.CreateOrderUIController;
//import ui.EditProductUIController;
//import ui.EditUserUIController;
//import ui.OrdersUIController;
//import ui.PaymentUIController;
//import ui.PaymentsListUIController;
//import ui.ProductDetailsUIController;
//import ui.RecipeUIController;
//import ui.RestaurateurUIController;
//import ui.SuperAdminUIController;
//import ui.UserInfoUIController;
//
//public class AppLauncher extends Application {
//	
//	private Role userConnectedRole = Role.USER;
//	
//	private Stage primaryStage;
//	private AnchorPane authUI;
//	
//	private static AppLauncher instance = null;
//	public static AppLauncher getInstance() { return instance; }
//
//	//
//	private AuthUIController authUIController = null;
//	private EditUserUIController editUserUIController = null;
//	private AdminDashboardUIController adminUIController = null;
//	private UserInfoUIController userInfoUIController = null;
//	
//	//
//	private ChefDashboardUIController chefDashUIController = null;
//	private EditProductUIController editProductUIController = null;
//	private ProductDetailsUIController productDetailsUIController = null;
//	
//	//
//	private OrdersUIController ordersUIController = null;
//	private CreateOrderUIController createOrderUIController = null; 
//	
//	//
//	private SuperAdminUIController superAdminUIController = null;
//	
//	//
//	private RestaurateurUIController restaurateurUIController = null;
//	
//	// 
//	private PaymentUIController paymentUIController = null;
//	
//	//
//	private RecipeUIController recipeUIController = null;
//	
//	// 
//	private PaymentsListUIController paymentsListUIController = null;
//
//	@Override
//	public void start(Stage primaryStage) {
//		instance = this;
//		
//		this.primaryStage = primaryStage;
//		this.primaryStage.setTitle("Resto Management System");
//		
//		initRootLayout();
//	}
//	
//	// Initializes the root layout.
//	public void initRootLayout() {
//		try {
//			// Load root layout from fxml file.
//			authUI = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/AuthUI.fxml"));
//			// Show the scene containing the root layout.
//			Scene scene = new Scene(authUI);
//			primaryStage.setScene(scene);
//			primaryStage.setResizable(false); // Empêche le redimensionnement
//			primaryStage.show();
//		} catch (IOException e) { 
//			e.printStackTrace(); 
//		}
//	}
//	
//	/*
//	 * 
//	 * Super Admin
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenSuperAdminUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/SuperAdminUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        superAdminUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Super Administrator Dashboard");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	/*
//	 * 
//	 * User management
//	 * 
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenUserInfoUI(User user) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/UserInfoUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        userInfoUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("User Details");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        userInfoUIController.setUser(user);
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//    }
//	
//	@FXML
//    public void handleOpenAdminDashboardUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/AdminDashboardUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        adminUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Administrator Dashboard");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	@FXML
//    public void handleOpenEditUserUIForUpdate(User user) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/EditUserUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        editUserUIController = loader.getController();
//        
//        if(user != null) {
//        	editUserUIController.setUser(user);
//        }
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Edition d'utilisateur");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	@FXML
//    public void handleOpenEditUserUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/EditUserUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        editUserUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Edition d'utilisateur");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	/*
//	 * 
//	 * Product management
//	 * 
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenChefDashboardUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ChefDashboardUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        chefDashUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Chef Dashboard");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	@FXML
//    public void handleOpenEditProductUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/EditProductUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        editProductUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Edition de produit");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	@FXML
//    public void handleOpenEditProductUIForUpdate(Product product) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/EditProductUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        editProductUIController = loader.getController();
//        
//        if(product != null) {
//        	editProductUIController.setProduct(product);
//        }
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Edition de produit");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	@FXML
//    public void handleOpenProductDetailsUI(Product product) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ProductDetailsUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        productDetailsUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Produit Details");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        productDetailsUIController.setProduct(product);
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//    }
//	
//	/*
//	 * 
//	 * Restaurateur
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenRestaurateurUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/RestaurateurUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        restaurateurUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Restaurateur Dashboard");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.show();
//        authUIController.closeUI();
//    }
//	
//	
//	/*
//	 * 
//	 * Commandes
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenOrdersUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/OrdersUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        ordersUIController = loader.getController();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Orders Dashboard");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.initModality(Modality.APPLICATION_MODAL);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.showAndWait();
//    }
//	
//	@FXML
//    public void handleOpenEditOrdersUI(List<Product> products) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/CreateOrderUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        createOrderUIController = loader.getController();
//        createOrderUIController.setProducts(products);
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Create Order");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.initModality(Modality.APPLICATION_MODAL);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.showAndWait();
//    }
//	
//	/*
//	 * 
//	 * Paiement
//	 * 
//	 * 
//	 */
//	@FXML
//    public void handleOpenPaymentUI(Order order) throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/PaymentUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        paymentUIController = loader.getController();
//        paymentUIController.setOrder(order);
//        
//        // ordersUIController.quit();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Payment");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.initModality(Modality.APPLICATION_MODAL);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.showAndWait();
//    }
//	
//	@FXML
//    public void handleOpenPaymentsListUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/PaymentsListUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        paymentsListUIController = loader.getController();
//        
//        // ordersUIController.quit();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Payment");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.initModality(Modality.APPLICATION_MODAL);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.showAndWait();
//    }
//	
//	/*
//	 * 
//	 * Recette
//	 * 
//	 */
//	
//	@FXML
//    public void handleOpenRecipeUI() throws IOException {
//        // Charger le fichier FXML de la nouvelle fenêtre
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/RecipeUI.fxml"));
//        Parent newWindowRoot = loader.load();
//        recipeUIController = loader.getController();
//        
//        // ordersUIController.quit();
//        
//        // Créer une nouvelle scène
//        Scene newWindowScene = new Scene(newWindowRoot);
//        
//        // Créer un nouveau stage pour la nouvelle fenêtre
//        Stage newWindowStage = new Stage();
//        newWindowStage.setTitle("Recettes");
//        newWindowStage.setScene(newWindowScene);
//        newWindowStage.initModality(Modality.APPLICATION_MODAL);
//        newWindowStage.setResizable(false); // Empêche le redimensionnement
//        
//        // Afficher la nouvelle fenêtre
//        newWindowStage.showAndWait();
//    }
//	
//	/*
//	 * 
//	 * 
//	 * 
//	 */
//	
//	
//	
//	/*
//	 * 
//	 * App
//	 * 
//	 * 
//	 */
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//	
//	// Returns the main stage.
//	public Stage getPrimaryStage() {
//		return primaryStage;
//	}
//	
//	public AuthUIController getAuthUIController() {
//		return authUIController;
//	}
//
//	public void setAuthUIController(AuthUIController authUIController) {
//		this.authUIController = authUIController;
//	}
//
//	public EditUserUIController getEditUserUIController() {
//		return editUserUIController;
//	}
//
//	public void setEditUserUIController(EditUserUIController editUserUIController) {
//		this.editUserUIController = editUserUIController;
//	}
//
//	public AdminDashboardUIController getAdminUIController() {
//		return adminUIController;
//	}
//
//	public void setAdminUIController(AdminDashboardUIController adminUIController) {
//		this.adminUIController = adminUIController;
//	}
//
//	public UserInfoUIController getUserInfoUIController() {
//		return userInfoUIController;
//	}
//
//	public void setUserInfoUIController(UserInfoUIController userInfoUIController) {
//		this.userInfoUIController = userInfoUIController;
//	}
//
//	public ChefDashboardUIController getChefDashUIController() {
//		return chefDashUIController;
//	}
//
//	public void setChefDashUIController(ChefDashboardUIController chefDashUIController) {
//		this.chefDashUIController = chefDashUIController;
//	}
//
//	public EditProductUIController getEditProductUIController() {
//		return editProductUIController;
//	}
//
//	public void setEditProductUIController(EditProductUIController editProductUIController) {
//		this.editProductUIController = editProductUIController;
//	}
//
//	public ProductDetailsUIController getProductDetailsUIController() {
//		return productDetailsUIController;
//	}
//
//	public void setProductDetailsUIController(ProductDetailsUIController productDetailsUIController) {
//		this.productDetailsUIController = productDetailsUIController;
//	}
//
//	public SuperAdminUIController getSuperAdminUIController() {
//		return superAdminUIController;
//	}
//
//	public void setSuperAdminUIController(SuperAdminUIController superAdminUIController) {
//		this.superAdminUIController = superAdminUIController;
//	}
//
//	public RestaurateurUIController getRestaurateurUIController() {
//		return restaurateurUIController;
//	}
//
//	public void setRestaurateurUIController(RestaurateurUIController restaurateurUIController) {
//		this.restaurateurUIController = restaurateurUIController;
//	}
//
//	public Role getUserConnectedRole() {
//		return userConnectedRole;
//	}
//
//	public void setUserConnectedRole(Role userConnectedRole) {
//		this.userConnectedRole = userConnectedRole;
//	}
//
//	public OrdersUIController getOrdersUIController() {
//		return ordersUIController;
//	}
//
//	public void setOrdersUIController(OrdersUIController ordersUIController) {
//		this.ordersUIController = ordersUIController;
//	}
//
//	public CreateOrderUIController getCreateOrderUIController() {
//		return createOrderUIController;
//	}
//
//	public void setCreateOrderUIController(CreateOrderUIController creatOrderUIController) {
//		this.createOrderUIController = creatOrderUIController;
//	}
//	
//
//	public PaymentUIController getPaymentUIController() {
//		return paymentUIController;
//	}
//
//	public void setPaymentUIController(PaymentUIController paymentUIController) {
//		this.paymentUIController = paymentUIController;
//	}
//
//	public RecipeUIController getRecipeUIController() {
//		return recipeUIController;
//	}
//
//	public void setRecipeUIController(RecipeUIController recipeUIController) {
//		this.recipeUIController = recipeUIController;
//	}
//
//	public PaymentsListUIController getPaymentsListUIController() {
//		return paymentsListUIController;
//	}
//
//	public void setPaymentsListUIController(PaymentsListUIController paymentsListUIController) {
//		this.paymentsListUIController = paymentsListUIController;
//	}
//	
//}
