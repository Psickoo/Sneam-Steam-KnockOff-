package components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddToCart extends Stage {
    private Spinner<Integer> quantitySpinner;

    public AddToCart(String gameName, String gameDescription, int gamePrice) {
        setTitle("Add To Cart");

        // Create labels for game details
        Label nameLabel = new Label(gameName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        Text descriptionLabel = new Text(gameDescription);
        descriptionLabel.setFont(Font.font("Arial", 10));
        descriptionLabel.setWrappingWidth(100);
        Label priceLabel = new Label("$" + gamePrice);

        // Create spinner for quantity
        quantitySpinner = new Spinner<>(0, 10, 0);

        // Create button for Add To Cart
        Button addToCartButton = new Button("Add To Cart");
        addToCartButton.setOnAction(e -> handleAddToCartButton(gameName));

        // Create layout and add components
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameLabel, descriptionLabel, priceLabel, new Label("Quantity:"), quantitySpinner, addToCartButton);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(100, 400);

        // Set scene for the stage
        setScene(new Scene(layout, 300, 400));
    }

    private void handleAddToCartButton(String gameName) {
        int quantity = quantitySpinner.getValue();

        // TODO: Implement logic to add/update the cart based on the quantity
        // You may want to communicate with the main window or another class to update the cart.

        // Close the pop-up window
        close();
    }
}
