package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PastController {
	private DBManager manager = DBManager.getInstance();
	private User user = manager.getUser();
	
	@FXML Button menuButton;
	@FXML VBox transactionContainer;
	
	@FXML
	public void initialize() throws IOException {
		for (Transaction transaction : user.getTransactions()) {
			addTransactionBox(transaction);
		}
	}
	
	public void addTransactionBox(Transaction transaction) {
		VBox root = new VBox();
		root.setPrefHeight(50);
		root.setPrefWidth(413);
		root.setStyle("-fx-background-color: #a7f5c6;");
		
		HBox upper = new HBox();
		Separator separatorH = new Separator();
		separatorH.setOrientation(Orientation.HORIZONTAL);
		HBox lower = new HBox();
		
		Insets insets = new Insets(3, 3, 3, 3);
		
		Label vendorName = new Label();
		vendorName.setPrefWidth(120);
		vendorName.setText(transaction.getVendor().getName());
		HBox.setMargin(vendorName, insets);
		upper.getChildren().add(vendorName);
		
		Separator separator1 = new Separator();
		separator1.setOrientation(Orientation.VERTICAL);
		upper.getChildren().add(separator1);
		
		Label vendorLocation = new Label();
		vendorLocation.setPrefWidth(270);
		vendorLocation.setText(transaction.getVendor().getLocation());
		HBox.setMargin(vendorLocation, insets);
		upper.getChildren().add(vendorLocation);
		
		Label category = new Label();
		category.setPrefWidth(120);
		category.setText(transaction.getCategory());
		HBox.setMargin(category, insets);
		lower.getChildren().add(category);
		
		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator2);
		
		Label amount = new Label();
		amount.setPrefWidth(120);
		amount.setText("$ "+ String.valueOf(transaction.getAmount()));
		HBox.setMargin(amount, insets);
		lower.getChildren().add(amount);
		
		Separator separator3 = new Separator();
		separator3.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator3);
		
		Label date = new Label();
		date.setPrefWidth(130);
		date.setText(transaction.getDate().toString());
		HBox.setMargin(date, insets);
		lower.getChildren().add(date);
		
		root.getChildren().add(upper);
		root.getChildren().add(separatorH);
		root.getChildren().add(lower);
		
		transactionContainer.getChildren().add(root);
	}
	
	public void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
