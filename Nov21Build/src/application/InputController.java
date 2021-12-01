package application;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputController {
	private DBManager manager = DBManager.getInstance();
	private User user = manager.getUser();
	
	@FXML TextField vendorName;
	@FXML TextField vendorLocation;
	@FXML TextField amount;
	@FXML DatePicker date;
	@FXML ChoiceBox<String> category;
	@FXML TextField newType;
	@FXML Button confirmButton;
	@FXML Button enterButton;
	@FXML Button menuButton;
	
	@FXML
	private void initialize() {
		refreshCategories();
	}
	
	@FXML
	private void createTransaction(ActionEvent event) {
		String vendorName = this.vendorName.getText();
		String vendorLocation = this.vendorLocation.getText();
		Vendor vendor = new Vendor(vendorName, vendorLocation);
		
		// check if the amount field is empty
		if (this.amount.getText().isEmpty()) {
			System.out.println("please enter the amount");
			return;
		}
		double amount = Double.parseDouble(this.amount.getText());
		
		// check if the date field is empty
		LocalDate lDate = date.getValue();
		if (lDate == null) {
			System.out.println("please select a date");
			return;
		}
		int month = lDate.getMonthValue();
		int day = lDate.getDayOfMonth();
		int year = lDate.getYear();
		Date date = new Date(month, day, year);
		
		// check if the category field is empty
		String category = this.category.getValue();
		if (category == null) {
			System.out.println("please select a category");
			return;
		}
		
		Transaction transaction = new Transaction(vendor, category, date, amount);
		user.addTransaction(transaction);
		user.addToCategory(category, amount);
		manager.updateUserData(user);
		
		System.out.println("created transaction");
		clearAllFields();
	}
	
	@FXML
	private void createCategory(ActionEvent event) {
		String categoryName = newType.getText();
		if (user.createCategory(categoryName)) {
			System.out.println("created category");
		} else {
			System.out.println("category already exists");
		}
		
		manager.updateUserData(user);
		
		refreshCategories();
		newType.clear();
	}
	
	private void refreshCategories() {
		// refresh choices for the category field
		this.category.getItems().clear();
		
		for (String category : user.getCategories().keySet()) {
			this.category.getItems().add(category);
		}
	}
	
	private void clearAllFields() {
		vendorName.clear();
		vendorLocation.clear();
		amount.clear();
		date.setValue(null);
		category.setValue(null);
		newType.clear();
	}
	
	@FXML
	private void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}