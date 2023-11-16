package ui;

import java.io.IOException;

import datas.BaseAccess;
import enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import master.AppLauncher;
import models.User;

public class AdminDashboardUIController {
	@FXML
    private Button quitButton;
	@FXML
    private ListView<User> usersListView;
	@FXML
	private TableView<User> usersTableView;
	@FXML
	private TableColumn<User, String> idColumn;
	@FXML
	private TableColumn<User, String> firstnameColumn;
	@FXML
	private TableColumn<User, String> lastnameColumn;
	@FXML
	private TableColumn<User, String> typeColumn;
	
	private User userSelected;
	
	ObservableList<User> usersList = FXCollections.observableArrayList();
	
	@FXML
    private void initialize() {
    	AppLauncher.getInstance().setAdminUIController(this);
    	
    	initUsersTableView();
    	
    	if(AppLauncher.getInstance().getUserConnectedRole() == Role.SUPERADMINISTRATOR) {
    		quitButton.setVisible(false);
    	}
    }
	
	private void initUsersTableView() {
		usersList.clear();
		usersList.addAll(BaseAccess.getInstance().getUsers());
		// TableView
    	usersTableView.setItems(usersList);
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    	lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    	typeColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    	// typeColumn.setCellFactory(column -> new UserTypeTableCell());
    	
    	usersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    	    // Gérer l'événement de sélection ici
    	    if (newValue != null) {
    	        // Vous pouvez accéder aux propriétés de l'utilisateur sélectionné
    	        
    	        // Faites ce que vous voulez avec les informations de l'utilisateur sélectionné
    	    	userSelected = newValue;
    	    }
    	});
	}
	
	
	public AdminDashboardUIController() { }
	
	@FXML
	private void openUserDetailsUI() {
		if(userSelected != null) {
			try {
				AppLauncher.getInstance().handleOpenUserInfoUI(userSelected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un utilisateur d'abord !");
		}
	}
	
	@FXML
	private void openAddUserUI() {
		try {
			AppLauncher.getInstance().handleOpenEditUserUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void openUpdateUserUI() {
		if(userSelected != null) {
			try {
				AppLauncher.getInstance().handleOpenEditUserUIForUpdate(userSelected);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un utilisateur d'abord !");
		}
	}
	
	@FXML
	private void deleteUser() {
		if(userSelected != null) {
			BaseAccess.getInstance().deleteUser(userSelected);
			initialize();
			userSelected = null;
			DialogHelper.showInformation("Information", "Suppression réussie !");
		} else {
			DialogHelper.showWarning("Attention", "Sélectionnez un utilisateur d'abord !");
		}
	}
	
	@FXML
	private void logout() {
		Stage stage = (Stage) usersTableView.getScene().getWindow();
        stage.close();
		AppLauncher.getInstance().initRootLayout();
	}
}
