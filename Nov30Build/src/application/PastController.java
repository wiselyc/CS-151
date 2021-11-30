package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PastController {
	
	/** Connection to the database. */
	private DbConnection connection = DbConnection.getInstance();
	/** The User object. */
	private User user = connection.getUser();
	/** The list of Transactions to be shown on the scene. */
	private ArrayList<Transaction> transactions = user.getTransactions();
	
	@FXML Button menuButton;
	@FXML VBox transactionContainer;
	@FXML DatePicker dateFilter;
	@FXML Button clearDate;
	@FXML ChoiceBox<String> categoryFilter;
	@FXML ChoiceBox<String> searchFilter;
	@FXML TextField keywords;
	@FXML Button enterButton;
	@FXML ChoiceBox<String> sortBy;
	@FXML Label transactionCount;
	
	/**
	 * Initialize the PastTransactions scene.
	 */
	@FXML
	private void initialize() {
		addAllTransactions();
		initializeCategoryFilter();
		initializeSearchFilter();
		initializeSortBy();
		refreshCount();
	}
	
	/**
	 * Add a box that contains the information of the Transaction.
	 * @param transaction the Transaction that contains the information to be shown on the scene
	 */
	private void addTransactionBox(Transaction transaction) {
		// set up the root box
		VBox root = new VBox();
		root.setPrefHeight(50);
		root.setPrefWidth(430);
		root.setStyle("-fx-background-color: #a7f5c6;");
		
		// set up the root box
		HBox upper = new HBox();
		Separator separatorH = new Separator();
		separatorH.setOrientation(Orientation.HORIZONTAL);
		HBox lower = new HBox();
		
		// define insets
		Insets insets = new Insets(3, 3, 3, 3);
		
		// Vendor name
		Label vendorName = new Label();
		vendorName.setPrefWidth(120);
		vendorName.setText(transaction.getVendor().getName());
		HBox.setMargin(vendorName, insets);
		upper.getChildren().add(vendorName);
		
		// separator
		Separator separator1 = new Separator();
		separator1.setOrientation(Orientation.VERTICAL);
		upper.getChildren().add(separator1);
		
		// Vendor location
		Label vendorLocation = new Label();
		vendorLocation.setPrefWidth(270);
		vendorLocation.setText(transaction.getVendor().getLocation());
		HBox.setMargin(vendorLocation, insets);
		upper.getChildren().add(vendorLocation);
		
		// Category
		Label category = new Label();
		category.setPrefWidth(120);
		category.setText(transaction.getCategory());
		HBox.setMargin(category, insets);
		lower.getChildren().add(category);
		
		// separator
		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator2);
		
		// amount
		Label amount = new Label();
		amount.setPrefWidth(120);
		amount.setText("$ "+ String.valueOf(transaction.getAmount()));
		HBox.setMargin(amount, insets);
		lower.getChildren().add(amount);
		
		// separator
		Separator separator3 = new Separator();
		separator3.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator3);
		
		// Date
		Label date = new Label();
		date.setPrefWidth(130);
		date.setText(transaction.getDate().toString());
		HBox.setMargin(date, insets);
		lower.getChildren().add(date);
		
		// set up the root box
		root.getChildren().add(upper);
		root.getChildren().add(separatorH);
		root.getChildren().add(lower);
		
		// show the box on the scene
		transactionContainer.getChildren().add(root);
	}
	
	/**
	 * Add filtered Transactions to the scene.
	 */
	private void addTransactions() {
		for (Transaction transaction : this.transactions) {
			addTransactionBox(transaction);
		}
	}
	
	/**
	 * Add all Transactions to the scene.
	 */
	private void addAllTransactions() {
		for (Transaction transaction : user.getTransactions()) {
			addTransactionBox(transaction);
		}
	}
	
	/**
	 * Clear the box that contains the Transactions.
	 */
	private void clearBox() {
		transactionContainer.getChildren().clear();
	}
	
	/**
	 * Refresh the box that contains the Transactions.
	 */
	private void refreshBox() {
		clearBox();
		addTransactions();
	}
	
	/**
	 * Clear the DatePicker.
	 */
	@FXML
	private void clearDateFilter() {
		dateFilter.setValue(null);
		filter();
	}
	
	/**
	 * Initialize the categoryFilter ChoiceBox.
	 */
	private void initializeCategoryFilter() {
		categoryFilter.getItems().add("Show all");
		
		// add the User's Categories
		for (Category category : user.getCategories().values()) {
			categoryFilter.getItems().add(category.getName());
		}
	}
	
	/**
	 * Initialize the searchFilter ChoiceBox.
	 */
	private void initializeSearchFilter() {
		searchFilter.getItems().add("Vendor name");
		searchFilter.getItems().add("Vendor location");
	}
	
	/**
	 * Initialize the sortBy ChoiceBox.
	 */
	private void initializeSortBy() {
		String[] options = new String[] {
				"Date (ascending)", "Date (descending)",
				"Amount (ascending)", "Amount (descending)"
		};
		
		sortBy.getItems().addAll(options);
	}
	
	/**
	 * Filter the list of Transactions.
	 */
	@FXML
	private void filter() {
		this.transactions = user.getTransactions();
		
		// filters
		filterByDate();
		filterByCategory();
		searchBy();
		
		// sort
		sortBy();
		
		// refresh the scene
		refreshBox();
		refreshCount();
	}
	
	/**
	 * Filter the list of Transactions by Date.
	 */
	private void filterByDate() {
		// get the Date
		LocalDate lDate = dateFilter.getValue();
		if (lDate == null) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		// if the Transaction's Date matches, add it to the result list
		for (Transaction transaction : transactions) {
			Date date = transaction.getDate();
			if (date.getMonth() == lDate.getMonthValue()
					&& date.getDay() == lDate.getDayOfMonth()
					&& date.getYear() == lDate.getYear()) {
				result.add(transaction);
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Filter the list of Transactions by Category.
	 */
	private void filterByCategory() {
		// get the Category
		String category = categoryFilter.getValue();
		if (category == null || category.equals("Show all")) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		// if the Transaction's Category matches, add it to the result list
		for (Transaction transaction : transactions) {
			if (transaction.getCategory().equals(category)) {
				result.add(transaction);
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Filter the list of Transactions by Vendor name or Vendor location and keywords.
	 */
	private void searchBy() {
		// get the keywords
		String keywords = this.keywords.getText();
		if (keywords.equals("")) return;
		if (searchFilter.getValue() == null) return;
		
		// get the searching method
		String searchBy = this.searchFilter.getValue();
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		if (searchBy.equals("Vendor name")) {
			// if the Transaction's Vendor name matches, add it to the result list
			for (Transaction transaction : transactions) {
				if (transaction.getVendor().getName().toLowerCase()
						.contains(keywords.toLowerCase())) {
					result.add(transaction);
				}
			}
		} else {
			// if the Transaction's Vendor location matches, add it to the result list
			for (Transaction transaction : user.getTransactions()) {
				if (transaction.getVendor().getLocation().toLowerCase()
						.contains(keywords.toLowerCase())) {
					result.add(transaction);
				}
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Sort the list of Transactions.
	 */
	@FXML
	private void sortBy() {
		// get the sorting method
		String val = sortBy.getValue();
		if (val == null) return;
		
		if (val.equals("Date (ascending)")) {
			// date (ascending)
			Collections.sort(transactions, (a, b) -> a.getDate().compareTo(b.getDate()));
		} else if (val.equals("Date (descending)")) {
			// date (descending)
			Collections.sort(transactions, (a, b) -> b.getDate().compareTo(a.getDate()));
		} else if (val.equals("Amount (ascending)")) {
			// amount (ascending)
			Collections.sort(transactions, (a, b) -> (int) Math.floor(a.getAmount() - b.getAmount()));
		} else {
			// amount (descending)
			Collections.sort(transactions, (a, b) -> (int) Math.floor(b.getAmount() - a.getAmount()));
		}
		
		refreshBox();
	}
	
	/**
	 * Refresh the number of filtered Transactions.
	 */
	private void refreshCount() {
		transactionCount.setText(String.valueOf(transactions.size()));
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
