package apps;

import components.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainApp extends Application {
	private static int userIndex = 0;
    private Stage primaryStage;
    private BorderPane loginRoot;
    private BorderPane registrationRoot;
    private static String loggedInUsername;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SNeam Digital Store");
        loginRoot = createLoginScene();
        showLoginScene();
    }

    private BorderPane createLoginScene() {
        BorderPane root = new BorderPane();
        GridPane gp = new GridPane();
        gp.setStyle("-fx-padding: 20; -fx-alignment: center");
        gp.setMinSize(600, 400);

        Label titleLabel = new Label("LOGIN");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMinWidth(200);

        Label emailTitle = new Label("Email");
        emailTitle.setAlignment(Pos.BASELINE_LEFT);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");
        emailField.setMinWidth(200);

        Label passTitle = new Label("Password");
        passTitle.setAlignment(Pos.BASELINE_LEFT);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMinWidth(200);

        HBox hb = new HBox();
        Button loginButton = new Button("Sign In");
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (validateLogin(email, password)) {
            	loggedInUsername = getUsernameByEmail(email);
                showHomePage(loggedInUsername);

            } else {
                showAlert("Login Failed", "Invalid username or password", Alert.AlertType.WARNING);
            }
        });

	    gp.add(titleLabel, 0, 0, 3, 1);
	
	    gp.add(emailTitle, 0, 1);
	    gp.add(emailField, 0, 2 , 3 , 1 );
	
	    gp.add(passTitle, 0, 3);
	    gp.add(passwordField, 0, 4 , 3, 1); 
	    gp.add(hb, 0, 5, 3, 1);
	    hb.getChildren().add(loginButton);

     	hb.setAlignment(Pos.CENTER);
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        root.setCenter(gp);

        root.setPrefWidth(300);
        root.setPrefHeight(200);

        return root;
    }
    
	private boolean validateLogin(String email, String password) {
        Connect con = Connect.getConnection();

        String query = "SELECT * FROM user WHERE Email = ? AND Password = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if the result set has any rows
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showLoginScene() {
        loginRoot = createLoginScene();
        primaryStage.setScene(new Scene(loginRoot));
        primaryStage.show();
    }

    private BorderPane createRegistrationScene() {
        BorderPane root = new BorderPane();

        GridPane center = new GridPane();
        center.setStyle("-fx-padding: 20; -fx-alignment: center");
        center.setMinSize(600, 400);

        Label titleLabel = new Label("REGISTRATION");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMinWidth(200);

        Label usernameTitle = new Label("Username");
        usernameTitle.setAlignment(Pos.BASELINE_LEFT);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setMinWidth(200);

        Label emailTitle = new Label("Email");
        emailTitle.setAlignment(Pos.BASELINE_LEFT);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");
        emailField.setMinWidth(200);

        Label passTitle = new Label("Password");
        passTitle.setAlignment(Pos.BASELINE_LEFT);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMinWidth(200);

        Label confirmPassTitle = new Label("Confirm Password");
        confirmPassTitle.setAlignment(Pos.BASELINE_LEFT);
        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPromptText("Confirm Password");
        confirmPassField.setMinWidth(200);

        Label phoneNumberTitle = new Label("Phone Number");
        phoneNumberTitle.setAlignment(Pos.BASELINE_LEFT);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter Phone Number");
        phoneNumberField.setMinWidth(200);

        // Register button
        HBox hb = new HBox();
        Button registerButton = new Button("Sign Up");
        registerButton.setAlignment(Pos.CENTER);
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPass = confirmPassField.getText();
            String phoneNumber = phoneNumberField.getText();

            validasiRegis(username, email, password, confirmPass, phoneNumber);
        });

        center.add(titleLabel, 0, 0, 3, 1);

        center.add(usernameTitle, 0, 1); 
        center.add(usernameField, 0, 2, 3, 1); 

        center.add(emailTitle, 0, 3);
        center.add(emailField, 0, 4, 3, 1); 

        center.add(passTitle, 0, 5); 
        center.add(passwordField, 0, 6, 3, 1); 

        center.add(confirmPassTitle, 0, 7);
        center.add(confirmPassField, 0, 8, 3, 1);

        center.add(phoneNumberTitle, 0, 9);
        center.add(phoneNumberField, 0, 10, 3, 1); 

        center.add(hb, 0, 11, 3, 1); 
        hb.getChildren().add(registerButton);

        hb.setAlignment(Pos.CENTER);
        center.setAlignment(Pos.CENTER);
        center.setHgap(10);
        center.setVgap(10);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        root.setCenter(center);

        root.setPrefWidth(300);
        root.setPrefHeight(200);

        return root;
    }


    private boolean validasiRegis(String username, String email, String password,
            String confirmPass, String phoneNumber) {
    	
    	String userID = generateUniqueUserID();
    	
		if (username.length() < 4 || username.length() > 20) {
			showAlert("Invalid Registration", "Username must be between 4 and 20 characters.", Alert.AlertType.WARNING);
			return false;
		}
		
		if (!email.contains("@")) {
			showAlert("Invalid Registration", "Invalid email format need '@'.", Alert.AlertType.WARNING);
			return false;
		}
		
		if (!validasiEmail(email)) {
			showAlert("Invalid Registration", "Email is already registered.", Alert.AlertType.WARNING);
			return false;
		}
		
		if (password.length() < 6 || password.length() > 20 || !password.matches("^[a-zA-Z0-9]+$")) {
			showAlert("Invalid Registration", "Password must be alphanumeric and between 6 and 20 characters.", Alert.AlertType.WARNING);
			return false;
		}
		
		if (!confirmPass.equals(password)) {
			showAlert("Invalid Registration", "Confirm Password does not match Password.", Alert.AlertType.WARNING);
			return false;
		}
		
		if (!phoneNumber.matches("\\d+") || phoneNumber.length() < 9 || phoneNumber.length() > 20) {
			showAlert("Invalid Registration", "Phone Number must be numeric and between 9 and 20 numbers.", Alert.AlertType.WARNING);
			return false;
		}
		
		
		insertUserToDatabase(userID, username, email, password, phoneNumber, "Customer");
		
		showAlert("Registration Successful", "You have successfully registered!", Alert.AlertType.INFORMATION);
				
		return true;
}

    private String generateUniqueUserID() {
        String baseUserID = "AC";
        int index = userIndex + 1; 

        while (true) {
            String newUserID = baseUserID + String.format("%03d", index);

            if (!validasiUserID(newUserID)) {
                return newUserID;
            }

            index++;
        }
    }
    private boolean validasiUserID(String userID) {
        Connect con = Connect.getConnection();

        String query = "SELECT * FROM user WHERE UserID = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	private void insertUserToDatabase(String userID, String username, String email, String password,String phoneNumber, String role) {
        Connect con = Connect.getConnection();

        String query = "INSERT INTO user (UserID, Username, Email, Password, PhoneNumber, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, role);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User inserted successfully.");
            } else {
                System.out.println("Failed to insert user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    private boolean validasiEmail(String email) {
        Connect con = Connect.getConnection();

        // Query to check if the email is unique
        String query = "SELECT * FROM user WHERE Email = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                return !resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void showRegistrationScene() {
        registrationRoot = createRegistrationScene(); // Create a new BorderPane for the registration scene
        primaryStage.setScene(new Scene(registrationRoot));
        primaryStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu navigateMenu = new Menu("Menu");

        MenuItem loginMenuItem = new MenuItem("Login");
        loginMenuItem.setOnAction(e -> showLoginScene());

        MenuItem registerMenuItem = new MenuItem("Register");
        registerMenuItem.setOnAction(e -> showRegistrationScene());

        navigateMenu.getItems().addAll(loginMenuItem, registerMenuItem);
        menuBar.getMenus().add(navigateMenu);

        return menuBar;
    }
    
    private String getUsernameByEmail(String email) {
        Connect con = Connect.getConnection();
        String query = "SELECT Username FROM user WHERE Email = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Username");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void showHomePage(String username) {
        HomePage homePage = new HomePage(username);
        primaryStage.setScene(new Scene(new BorderPane())); // Set an empty scene temporarily

        // Access the root of the HomePage after the UI components are initialized
        Platform.runLater(() -> {
            BorderPane homePageRoot = homePage.getRoot();
            primaryStage.setScene(new Scene(homePageRoot)); // Set the actual scene
            primaryStage.show();	
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
