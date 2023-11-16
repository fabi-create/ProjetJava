package ui;

import datas.BaseAccess;
import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import master.AppLauncher;
import models.Administrator;
import models.Chef;
import models.Restaurateur;
import models.SuperAdministrator;
import models.User;

public class EditUserUIController {
	@FXML
    private Label inputErrorText;
	@FXML
    private Label successText;
	@FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ComboBox<Role> roleComboBox;
    
    private User user;
    
	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setEditUserUIController(this);
    	roleComboBox.getItems().clear();
        roleComboBox.getItems().addAll(Role.values());
        
        inputErrorText.textProperty().set("");
		successText.setVisible(false);
		
		displayUserDetails();
    }
	
	private void displayUserDetails() {
		if(user != null) {
			firstnameTextField.setText(user.getFirstname());
			lastnameTextField.setText(user.getLastname());
			loginTextField.setText(user.getLogin());
			passwordTextField.setText(user.getPassword());
		}
	}
	
	@FXML
	private void validEdition() {
		if(firstnameTextField.getText().isEmpty() || 
			lastnameTextField.getText().isEmpty() || 
			loginTextField.getText().isEmpty() ||
			passwordTextField.getText().isEmpty() || 
			roleComboBox.getSelectionModel().isEmpty()) {
				inputErrorText.textProperty().set("Les champs ne sont pas valident");
				DialogHelper.showWarning("Attention", "Les champs ne sont pas valident");
		} else {
			String roleValue = roleComboBox.getSelectionModel().getSelectedItem().getRole();
			
			User userAdded = null;
			if(roleValue.equals("Chef")) {
				Chef objUser = new Chef(lastnameTextField.getText(), 
						firstnameTextField.getText(), 
						loginTextField.getText(), 
						passwordTextField.getText(), 
						roleValue
				);
				// Pour l'ajout
				if(user == null) {
					userAdded = BaseAccess.getInstance().addUser((Chef) objUser);
				}  else {
					// Pour la mise à jour 
					objUser.setId(user.getId());
					userAdded = BaseAccess.getInstance().updateUser((Chef) objUser);
				}
			} else if(roleValue.equals("SuperAdministrator")) {
				SuperAdministrator objUser = new SuperAdministrator(lastnameTextField.getText(), 
						firstnameTextField.getText(), 
						loginTextField.getText(), 
						passwordTextField.getText(), 
						roleValue
				);
				// Pour l'ajout
				if(user == null) {
					userAdded = BaseAccess.getInstance().addUser((SuperAdministrator) objUser);
				}  else {
					// Pour la mise à jour 
					objUser.setId(user.getId());
					userAdded = BaseAccess.getInstance().updateUser((SuperAdministrator) objUser);
				}
			} else if(roleValue.equals("Administrator")) {
				Administrator objUser = new Administrator(lastnameTextField.getText(), 
						firstnameTextField.getText(), 
						loginTextField.getText(), 
						passwordTextField.getText(), 
						roleValue
				);
				// Pour l'ajout
				if(user == null) {
					userAdded = BaseAccess.getInstance().addUser((Administrator) objUser);
				}  else {
					// Pour la mise à jour 
					objUser.setId(user.getId());
					userAdded = BaseAccess.getInstance().updateUser((Administrator) objUser);
				}
			} else if(roleValue.equals("Restaurateur")) {
				Restaurateur objUser = new Restaurateur(lastnameTextField.getText(), 
						firstnameTextField.getText(), 
						loginTextField.getText(), 
						passwordTextField.getText(), 
						roleValue
				);
				// Pour l'ajout
				if(user == null) {
					userAdded = BaseAccess.getInstance().addUser((Restaurateur) objUser);
				}  else {
					// Pour la mise à jour 
					objUser.setId(user.getId());
					userAdded = BaseAccess.getInstance().updateUser((Restaurateur) objUser);
				}
			}
			
			if(userAdded != null) {
				successText.setVisible(true);
				DialogHelper.showInformation("Information", successText.getText());
				clearInputs();
			} else {
				successText.setVisible(false);
			}
		}
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		displayUserDetails();
	}
	
	@FXML
	private void clearInputs() {
		loginTextField.clear();
    	passwordTextField.clear();
    	firstnameTextField.clear();
    	lastnameTextField.clear();
    	inputErrorText.textProperty().set(".");
    	
    	user = null;
	}
	
	@FXML
	private void closeUI() {
		Stage stage = (Stage) firstnameTextField.getScene().getWindow();
        stage.close();
	}
}
