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
	
	/** Connection to the database. */
	private DbConnection connection = DbConnection.getInstance();
	/** The User object. */
	private User user = connection.getUser();
	
	@FXML TextField vendorName;
	@FXML TextField vendorLocation;
	@FXML TextField amount;
	@FXML DatePicker date;
	@FXML ChoiceBox<String> category;
	@FXML TextField newType;
	@FXML Button confirmButton;
	@FXML Button enterButton;
	@FXML Button menuButton;
	
	/**
	 * Initialize the InputTransaction scene.
	 */
	@FXML
	private void initialize() {
		refreshCategories();
	}
	
	/**
	 * Create a new Transaction.
	 * @param event
	 */
	@FXML
	private void createTransaction(ActionEvent event) {
		String vendorName = this.vendorName.getText();
		String vendorLocation = this.vendorLocation.getText();
		Vendor vendor = new Vendor(vendorName, vendorLocation);
		
		// check if the amount field is empty and get the amount
		if (this.amount.getText().isEmpty()) {
			System.out.println("please enter the amount");
			return;
		}
		double amount = Double.parseDouble(this.amount.getText());
		
		// check if the Date field is empty and get the Date
		LocalDate lDate = date.getValue();
		if (lDate == null) {
			System.out.println("please select a date");
			return;
		}
		int month = lDate.getMonthValue();
		int day = lDate.getDayOfMonth();
		int year = lDate.getYear();
		Date date = new Date(month, day, year);
		
		// check if the Category field is empty and get the Category
		String category = this.category.getValue();
		if (category == null) {
			System.out.println("please select a category");
			return;
		}
		
		// create a new Transaction
		Transaction transaction = new Transaction(vendor, category, date, amount);
		
		// update the User object and database
		user.addTransaction(transaction);
		user.addToCategory(category, amount);
		connection.updateUserData(user);
		
		// clear fields
		clearAllFields();
	}
	
	/**
	 * Create a new Category.
	 * @param event
	 */
	@FXML
	private void createCategory(ActionEvent event) {
		String categoryName = newType.getText();
		
		// create a new Category if it does not exist
		if (user.createCategory(categoryName)) {
		} else {
			System.out.println("category already exists");
		}
		
		// update the database
		connection.updateUserData(user);
		
		// refresh the Category ChoiceBox
		refreshCategories();
		newType.clear();
	}
	
	/**
	 * Refresh the Category ChoiceBox.
	 */
	private void refreshCategories() {
		// refresh choices for the Category ChoiceBox
		this.category.getItems().clear();
		
		// add Categories to the ChoiceBox
		for (Category category : user.getCategories().values()) {
			this.category.getItems().add(category.getName());
		}
	}
	
	/**
	 * Clear all fields in the scene.
	 */
	private void clearAllFields() {
		vendorName.clear();
		vendorLocation.clear();
		amount.clear();
		date.setValue(null);
		category.setValue(null);
		newType.clear();
	}
	
	/**
	 * Switch to the Menu scene.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
