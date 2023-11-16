package ui;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import models.ProductDetails;
public class QuantityEditingCell extends TableCell<ProductDetails, Integer> {
    private final Spinner<Integer> spinner = new Spinner<>();

    public QuantityEditingCell() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (!isEmpty()) {
                commitEdit(newValue);
            }
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (!isEmpty()) {
            setGraphic(spinner);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setGraphic(spinner);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                spinner.getValueFactory().setValue(item);
            }
        }
    }
}
