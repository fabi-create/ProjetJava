package ui;

import java.io.IOException;

import datas.BaseAccess;
import enums.Role;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Administrator;
import models.Chef;
import models.Restaurateur;
import models.SuperAdministrator;
import models.User;

public class AuthUIController {
	@FXML
    private Label loginErrorText;
	@FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button validButton;
    @FXML
    private Button quitButton;
    
    
    // private boolean isLoginCorrect = false;
    
    
    public AuthUIController() {  }
    
    @FXML
    private void initialize() {
    	loginErrorText.textProperty().set("_");
    	AppLauncher.getInstance().setAuthUIController(this);
    }
    
    // Connexion
    @FXML
    private void toLogin() {
    	if(loginTextField.textProperty().get().isEmpty() || loginTextField.textProperty().get().isEmpty()) {
    		// isLoginCorrect = false;
    		loginErrorText.textProperty().set("Les champs doivent être remplis");
    		DialogHelper.showWarning("Attention", "Les champs doivent être remplis");
    	} else {
    		User user = BaseAccess.getInstance()
    				.readUser(loginTextField.textProperty().get().toString(), 
    						passwordTextField.textProperty().get().toString());
    		if(user != null) {
    			loginErrorText.textProperty().set("Utilisateur Trouvé");
    			// Si c'est un admin
    			if(user.getClass() == SuperAdministrator.class) {
    				try {
    					clearInputs();
    					AppLauncher.getInstance().setUserConnectedRole(Role.SUPERADMINISTRATOR);
    					AppLauncher.getInstance().handleOpenSuperAdminUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}else if(user.getClass() == Administrator.class) {
    				try {
    					clearInputs();
    					AppLauncher.getInstance().setUserConnectedRole(Role.ADMINISTRATOR);
    					AppLauncher.getInstance().handleOpenAdminDashboardUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			} else if(user.getClass() == Chef.class) {
    				try {
    					clearInputs();
    					AppLauncher.getInstance().setUserConnectedRole(Role.CHEF);
    					AppLauncher.getInstance().handleOpenChefDashboardUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			} else if(user.getClass() == Restaurateur.class) {
    				try {
    					clearInputs();
    					AppLauncher.getInstance().setUserConnectedRole(Role.RESTAURATEUR);
    					AppLauncher.getInstance().handleOpenRestaurateurUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			} 
    		} else {
    			loginErrorText.textProperty().set("Utilisateur non retrouvé");
    			DialogHelper.showError("Erreur", "Utilisateur non retrouvé !");
    		}
    	}
    }
    
    
    @FXML
    private void clearInputs() {
    	loginTextField.clear();
    	passwordTextField.clear();
    	loginErrorText.textProperty().set(".");
    }
    
    @FXML
    public void closeUI() { 
    	Stage stage = (Stage) loginTextField.getScene().getWindow();
        stage.close();
    }
    
    
    // Quitter la fenêtre et l'application
    @FXML
    private void closeApp() {
    	// Ferme toutes les fenêtres ouvertes et quitte l'application
        Platform.exit();
    }
    
}
