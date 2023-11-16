package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import master.AppLauncher;

public class SuperAdminUIController {
	
	public SuperAdminUIController() { }
	
	@FXML
	private void goToAdminUI() {
		try {
			AppLauncher.getInstance().handleOpenAdminDashboardUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void goToChefUI() {
		try {
			AppLauncher.getInstance().handleOpenChefDashboardUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void goToRestaurateurUI() {
		try {
			AppLauncher.getInstance().handleOpenRestaurateurUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
