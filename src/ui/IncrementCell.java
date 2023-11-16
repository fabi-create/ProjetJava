package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import models.ProductDetails;

public class IncrementCell extends TableCell<ProductDetails, Integer> {
   private final Button incrementButton;
   private final Button decrementButton;
   
   public IncrementCell() {
        incrementButton = new Button("+");
        decrementButton = new Button("-");
        
        incrementButton.setOnAction(event -> incrementValue());
        decrementButton.setOnAction(event -> decrementValue());
    }

    private void incrementValue() {
        int currentValue = getItem();
        setItem(currentValue + 1);
    }

    private void decrementValue() {
        int currentValue = getItem();
        setItem(currentValue - 1);
    }
    
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(incrementButton);
        }
    }
}
