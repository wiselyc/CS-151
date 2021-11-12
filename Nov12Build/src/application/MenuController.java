package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
	@FXML Button inputButton;
	@FXML Button pastButton;
	@FXML Button summaryButton;
	
	public void switchToInput(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Input.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) inputButton.getScene().getWindow();
		stage.setScene(scene);
	}
	
	public void switchToPast(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Past.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) pastButton.getScene().getWindow();
		stage.setScene(scene);
	}
	
	public void switchToSummary(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) summaryButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
