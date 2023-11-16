package ui;

import java.io.IOException;

import datas.BaseAccess;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import master.AppLauncher;
import models.User;

public class UserInfoUIController {

	@FXML
    private Label firstnameText;
	@FXML
    private Label lastnameText;
	@FXML
    private Label loginText;
	@FXML
    private Label passwordText;
	@FXML
    private Label roleText;
	
	private User user;

	public UserInfoUIController() {  }
	    
    @FXML
    private void initialize() {
    	AppLauncher.getInstance().setUserInfoUIController(null);
    	displayUserDetails();
    }
    
    private void displayUserDetails() {
    	if(user != null) {
    		firstnameText.textProperty().set(user.getFirstname());
    		lastnameText.textProperty().set(user.getLastname());
    		loginText.textProperty().set(user.getLogin());
    		passwordText.textProperty().set(user.getPassword());
    		roleText.textProperty().set(user.getRole());
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
	private void openUpdateUserUI() {
		if(user != null) {
			try {
				AppLauncher.getInstance().handleOpenEditUserUIForUpdate(user);
				closeUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void deleteUser() {
		if(user != null) {
			BaseAccess.getInstance().deleteUser(user);
			closeUI();
		}
	}
	
	@FXML
	private void closeUI() {
		Stage stage = (Stage) firstnameText.getScene().getWindow();
        stage.close();
	}
}
