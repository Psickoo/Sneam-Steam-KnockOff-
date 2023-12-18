package apps;

import components.AddToCart;
import components.Connect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePage extends Application {
    private ListView<String> gameListView;
    private VBox gameDetailsVBox;
    private String username;
    private BorderPane root;
    private boolean initialized = false;

    public HomePage(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SNeam Digital Store - Customer Home");

        gameListView = new ListView<>();
        gameListView.setPrefSize(300, 300);
        fetchAndDisplayGames();

        gameDetailsVBox = new VBox();
        gameDetailsVBox.setAlignment(Pos.CENTER);
        gameDetailsVBox.setSpacing(15);

        VBox vbox = new VBox(gameListView);
        vbox.setMinSize(300, 300);
        GridPane gp = new GridPane();
        Label titleLabel = new Label("Hello, " + username + "!");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMinWidth(580);

        gameListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fetchAndDisplayGameDetails(newValue);
            }
        });

        root = new BorderPane();
        MenuBar mb = createMenuBar();
        root.setTop(mb);
        root.setCenter(gp);

        gp.add(titleLabel, 0, 0, 1, 1);
        GridPane.setMargin(vbox, new Insets(50, 15, 0, 0));
        gp.add(vbox, 0, 1);
        gp.add(gameDetailsVBox, 1, 1);

        gp.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchAndDisplayGames() {
        Connect connect = Connect.getConnection();
        String query = "SELECT * FROM game";

        try {
            ResultSet resultSet = connect.executeQuery(query);

            ObservableList<String> gameNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String gameName = resultSet.getString("GameName");
                gameNames.add(gameName);
            }

            gameListView.setItems(gameNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchAndDisplayGameDetails(String selectedGame) {
        Connect connect = Connect.getConnection();
        String query = "SELECT * FROM game WHERE GameName = '" + selectedGame + "'";

        try {
            ResultSet resultSet = connect.executeQuery(query);

            while (resultSet.next()) {
                String gameName = resultSet.getString("GameName");
                String gameDescription = resultSet.getString("GameDescription");
                int price = resultSet.getInt("Price");
                updateGameDetails(gameName, gameDescription, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGameDetails(String gameName, String gameDescription, int price) {
        gameDetailsVBox.getChildren().clear();

        Text nameLabel = new Text(gameName);
        nameLabel.setWrappingWidth(280);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Text descriptionText = new Text("Description: " + gameDescription);
        descriptionText.setWrappingWidth(280);
        Label priceLabel = new Label("Price: $" + price);
        Button addToCart = new Button("Add To Cart");
        addToCart.setPrefSize(280, 35);

        addToCart.setOnAction(e -> showAddToCartScene(gameName, gameDescription, price));

        gameDetailsVBox.getChildren().addAll(nameLabel, descriptionText, priceLabel, addToCart);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu dashboard = new Menu("Dashboard");
        Menu logOut = new Menu("Log Out");
        menuBar.getMenus().addAll(dashboard, logOut);
        return menuBar;
    }

    private void showAddToCartScene(String gameName, String gameDescription, int price) {
        AddToCart addToCartScene = new AddToCart(gameName, gameDescription, price);

        // Set modality to make the pop-up window block interactions with the main window
        addToCartScene.initModality(Modality.APPLICATION_MODAL);
        addToCartScene.showAndWait();
    }

    private void initialize() {
        gameListView = new ListView<>();
        gameListView.setPrefSize(300, 300);
        fetchAndDisplayGames();

        gameDetailsVBox = new VBox();
        gameDetailsVBox.setAlignment(Pos.CENTER);
        gameDetailsVBox.setSpacing(15);

        VBox vbox = new VBox(gameListView);
        vbox.setMinSize(300, 300);
        GridPane gp = new GridPane();
        Label titleLabel = new Label("Hello, " + username + "!");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMinWidth(580);

        gameListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fetchAndDisplayGameDetails(newValue);
            }
        });

        root = new BorderPane();
        root.setMinSize(1000, 600);
        MenuBar mb = createMenuBar();
        root.setTop(mb);
        root.setCenter(gp);

        gp.add(titleLabel, 0, 0, 1, 1);
        GridPane.setMargin(vbox, new Insets(50, 15, 0, 0));
        gp.add(vbox, 0, 1);
        gp.add(gameDetailsVBox, 1, 1);

        gp.setAlignment(Pos.CENTER);

        initialized = true;
    }

    public BorderPane getRoot() {
        if (!initialized) {
            initialize();
        }
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
