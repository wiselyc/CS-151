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
	private DBManager manager = DBManager.getInstance();
	private User user = manager.getUser();
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
	
	@FXML
	private void initialize() throws IOException {
		addAllTransactions();
		initializeCategoryFilter();
		initializeSearchFilter();
		initializeSortBy();
		refreshCount();
	}
	
	private void addTransactionBox(Transaction transaction) {
		VBox root = new VBox();
		root.setPrefHeight(50);
		root.setPrefWidth(430);
		root.setStyle("-fx-background-color: #a7f5c6;");
		
		HBox upper = new HBox();
		Separator separatorH = new Separator();
		separatorH.setOrientation(Orientation.HORIZONTAL);
		HBox lower = new HBox();
		
		Insets insets = new Insets(3, 3, 3, 3);
		
		// vendor name
		Label vendorName = new Label();
		vendorName.setPrefWidth(120);
		vendorName.setText(transaction.getVendor().getName());
		HBox.setMargin(vendorName, insets);
		upper.getChildren().add(vendorName);
		
		Separator separator1 = new Separator();
		separator1.setOrientation(Orientation.VERTICAL);
		upper.getChildren().add(separator1);
		
		// vendor location
		Label vendorLocation = new Label();
		vendorLocation.setPrefWidth(270);
		vendorLocation.setText(transaction.getVendor().getLocation());
		HBox.setMargin(vendorLocation, insets);
		upper.getChildren().add(vendorLocation);
		
		// category
		Label category = new Label();
		category.setPrefWidth(120);
		category.setText(transaction.getCategory());
		HBox.setMargin(category, insets);
		lower.getChildren().add(category);
		
		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator2);
		
		// amount
		Label amount = new Label();
		amount.setPrefWidth(120);
		amount.setText("$ "+ String.valueOf(transaction.getAmount()));
		HBox.setMargin(amount, insets);
		lower.getChildren().add(amount);
		
		Separator separator3 = new Separator();
		separator3.setOrientation(Orientation.VERTICAL);
		lower.getChildren().add(separator3);
		
		// date
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
	
	private void addTransactions() {
		for (Transaction transaction : this.transactions) {
			addTransactionBox(transaction);
		}
	}
	
	private void addAllTransactions() {
		for (Transaction transaction : user.getTransactions()) {
			addTransactionBox(transaction);
		}
	}
	
	private void clearBox() {
		transactionContainer.getChildren().clear();
	}
	
	private void refreshBox() {
		clearBox();
		addTransactions();
	}
	
	@FXML
	private void clearDateFilter() {
		dateFilter.setValue(null);
		filter();
	}
	
	private void initializeCategoryFilter() {
		categoryFilter.getItems().add("Show all");
		
		for (String category : user.getCategories().keySet()) {
			categoryFilter.getItems().add(category);
		}
	}
	
	private void initializeSearchFilter() {
		searchFilter.getItems().add("Vendor name");
		searchFilter.getItems().add("Vendor location");
	}
	
	private void initializeSortBy() {
		String[] options = new String[] {
				"Date (ascending)", "Date (descending)",
				"Amount (ascending)", "Amount (descending)"
		};
		
		sortBy.getItems().addAll(options);
	}
	
	@FXML
	private void filter() {
		this.transactions = user.getTransactions();
		filterByDate();
		filterByCategory();
		searchBy();
		
		sortBy();
		refreshBox();
		refreshCount();
		
		System.out.println("applied filter(s)");
	}
	
	private void filterByDate() {
		LocalDate lDate = dateFilter.getValue();
		if (lDate == null) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
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
	
	private void filterByCategory() {
		String category = categoryFilter.getValue();
		if (category == null || category.equals("Show all")) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (transaction.getCategory().equals(category)) {
				result.add(transaction);
			}
		}
		
		transactions = result;
	}
	
	private void searchBy() {
		if (keywords.getText().equals("")) return;
		if (searchFilter.getValue() == null) return;
		
		String searchBy = this.searchFilter.getValue();
		ArrayList<Transaction> result = new ArrayList<>();
		if (searchBy.equals("Vendor name")) {
			for (Transaction transaction : transactions) {
				if (transaction.getVendor().getName().toLowerCase()
						.contains(keywords.getText().toLowerCase())) {
					result.add(transaction);
				}
			}
		} else {
			for (Transaction transaction : user.getTransactions()) {
				if (transaction.getVendor().getLocation().toLowerCase()
						.contains(keywords.getText().toLowerCase())) {
					result.add(transaction);
				}
			}
		}
		
		transactions = result;
	}
	
	@FXML
	private void sortBy() {
		String val = sortBy.getValue();
		if (val == null) return;
		
		if (val.equals("Date (ascending)")) {
			Collections.sort(transactions, (a, b) -> a.getDate().compareTo(b.getDate()));
		} else if (val.equals("Date (descending)")) {
			Collections.sort(transactions, (a, b) -> b.getDate().compareTo(a.getDate()));
		} else if (val.equals("Amount (ascending)")) {
			Collections.sort(transactions, (a, b) -> (int) Math.floor(a.getAmount() - b.getAmount()));
		} else {
			Collections.sort(transactions, (a, b) -> (int) Math.floor(b.getAmount() - a.getAmount()));
		}
		
		refreshBox();
	}
	
	private void refreshCount() {
		transactionCount.setText(String.valueOf(transactions.size()));
	}
	
	@FXML
	private void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
