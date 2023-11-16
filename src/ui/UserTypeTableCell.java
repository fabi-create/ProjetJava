package ui;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import models.User;

public class UserTypeTableCell extends TableCell<User, String> {
    @Override
    protected void updateItem(String role, boolean empty) {
        super.updateItem(role, empty);
        
        if (role == null || empty) {
            setText(null);
            setStyle("");
        } else {
            setText(role);
            // Définir un style différent en fonction du type d'utilisateur
            if (role.equals("Administrator")) {
                setTextFill(Color.RED);
            } else if (role.equals("Chef")) {
                setTextFill(Color.BLUE);
            } else {
                // Par défaut, utiliser la couleur par défaut
                setTextFill(Color.BLACK);
            }
        }
    }
}
