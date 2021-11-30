package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SummaryController {
	
	/** Connection to the database. */
	private DbConnection manager = DbConnection.getInstance();
	/** The User object. */
	private User user = manager.getUser();
	/** The list of Transactions. */
	private ArrayList<Transaction> transactions = user.getTransactions();
	
	@FXML PieChart pieChart;
	@FXML Button menuButton;
	@FXML ChoiceBox<String> searchFilter;
	@FXML TextField keywords;
	@FXML Button enterButton;
	@FXML DatePicker dateFrom;
	@FXML DatePicker dateTo;
	@FXML Button clearDateFromFilter;
	@FXML Button clearDateToFilter;
	
	/**
	 * Initialize the Summary scene.
	 */
	@FXML
	private void initialize() {
		initializeSearchFilter();
		generateChart();
	}
	
	/**
	 * Generate a PieChart.
	 */
	private void generateChart() {
		// if there is no Categories found, prompt the user to input a Transaction
		if (user.getCategories().isEmpty()) {
			pieChart.setTitle("Please input a new transaction");
			return;
		}
		
		// generate a chart using the User's Categories
		generateChart(user.getCategories().values());
	}
	
	/**
	 * Generate a PieChart with specified Categories.
	 * @param categories the Categories to be shown
	 */
	private void generateChart(Collection<Category> categories) {
		// clear the chart
		pieChart.getData().clear();
		
		double totalSpending = 0;
		
		// get the total spending to calculate percentage of each Category
		for (Category category : categories) {
			totalSpending += category.getSpending();
		}
		
		// add data to the pie chart
		for (Category category : categories) {
			double temp = category.getSpending() / totalSpending;
			String str = category.getName() + ", $" + category.getSpending() + "\n" + toPercentage(temp);
			PieChart.Data data = new PieChart.Data(str, category.getSpending());
			pieChart.getData().add(data);
		}
		
		pieChart.setLegendVisible(false);
	}
	
	/**
	 * Convert a number between 0 and 1 to percentage.
	 * @param number a number between 0 and 1
	 * @return the String representation of the percentage
	 */
	private String toPercentage(double number) {
		int temp = (int) Math.round(number * 100);
		return String.valueOf(temp) + "%";
	}
	
	/**
	 * Initialize the searchFilter ChoiceBox.
	 */
	private void initializeSearchFilter() {
		searchFilter.getItems().add("Vendor name");
		searchFilter.getItems().add("Vendor location");
	}
	
	/**
	 * Filter the list of Transactions.
	 */
	@FXML
	private void filter() {
		this.transactions = user.getTransactions();
		// filters
		searchBy();
		filterByDate();
		
		// create a HashMap using the filtered Transactions
		HashMap<String, Category> categories = new HashMap<>();
		for (Transaction transaction : transactions) {
			String name = transaction.getCategory();
			
			if (categories.containsKey(name)) {
				Category category = categories.get(name);
				category.addSpending(transaction.getAmount());
				categories.put(name, category);
			} else {
				Category temp = new Category(transaction.getCategory(), transaction.getAmount());
				categories.put(name, temp);
			}
		}
		
		// generate the chart
		generateChart(categories.values());
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
	 * Filter the list of Transactions by Date.
	 */
	private void filterByDate() {
		// get the LocalDates
		LocalDate from = dateFrom.getValue();
		LocalDate to = dateTo.getValue();
		if (from == null && to == null) return;
		if (from != null && to != null && from.isAfter(to)) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		
		// convert to Dates
		Date dFrom = from == null ? null : new Date(from.getMonthValue(), from.getDayOfMonth(), from.getYear());
		Date dTo = to == null ? null : new Date(to.getMonthValue(), to.getDayOfMonth(), to.getYear());
		
		for (Transaction transaction : transactions) {
			// if the Transaction's Date is between the specified Dates, add it to the result list
			boolean isBetween = true;
			
			if (dFrom != null && transaction.getDate().compareTo(dFrom) < 0) {
				isBetween = false;
			}
			if (dTo != null && transaction.getDate().compareTo(dTo) > 0) {
				isBetween = false;
			}
			
			if (isBetween) {
				result.add(transaction);
			}
		}
		
		transactions = result;
	}
	
	/**
	 * Clear the dateFrom DatePicker.
	 */
	@FXML
	private void clearDateFrom() {
		dateFrom.setValue(null);
		filter();
	}
	
	/**
	 * Clear the dateTo DatePicker.
	 */
	@FXML
	private void clearDateTo() {
		dateTo.setValue(null);
		filter();
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
