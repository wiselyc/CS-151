package controller;

import java.io.IOException;

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
	
	/**
	 * Switch to the InputTransaction scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToInput() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Input.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) inputButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Switch to the PastTransactions scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToPast() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Past.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) pastButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Switch to the Summary scene.
	 * @throws IOException
	 */
	@FXML
	private void switchToSummary() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Summary.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) summaryButton.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
