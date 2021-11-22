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
	private DBManager manager = DBManager.getInstance();
	private User user = manager.getUser();
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
	
	@FXML
	private void initialize() {
		initializeSearchFilter();
		
		generateGraph();
	}
	
	private void generateGraph() {
		// if there is no categories found, prompt the user to input a transaction
		if (user.getCategories().isEmpty()) {
			pieChart.setTitle("Please input a new transaction");
			return;
		}
		
		generateGraph(user.getCategories().values());
	}
	
	private void generateGraph(Collection<Category> categories) {
		pieChart.getData().clear();
		
		double totalSpending = 0;
		
		// get the total spending to calculate percentage of each category
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
		System.out.println("loaded categories");
	}
	
	private String toPercentage(double number) {
		int temp = (int) Math.round(number * 100);
		return String.valueOf(temp) + "%";
	}
	
	private void initializeSearchFilter() {
		searchFilter.getItems().add("Vendor name");
		searchFilter.getItems().add("Vendor location");
	}
	
	@FXML
	private void filter() {
		this.transactions = user.getTransactions();
		searchBy();
		filterByDate();
		
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
		
		generateGraph(categories.values());
		System.out.println("applied filter(s)");
	}
	
	private void searchBy() {
		if (keywords.getText().equals("")) return;
		if (searchFilter.getValue() == null) return;
		
		String searchBy = this.searchFilter.getValue();
		ArrayList<Transaction> result = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (searchBy.equals("Vendor name")) {
				if (transaction.getVendor().getName().toLowerCase()
						.contains(keywords.getText().toLowerCase())) {
					result.add(transaction);
				}
			} else {
				if (transaction.getVendor().getLocation().toLowerCase()
						.contains(keywords.getText().toLowerCase())) {
					result.add(transaction);
				}
			}
		}
		
		transactions = result;
	}
	
	private void filterByDate() {
		LocalDate from = dateFrom.getValue();
		LocalDate to = dateTo.getValue();
		if (from == null && to == null) return;
		if (from != null && to != null && from.isAfter(to)) return;
		
		ArrayList<Transaction> result = new ArrayList<>();
		Date dFrom = from == null ? null : new Date(from.getMonthValue(), from.getDayOfMonth(), from.getYear());
		Date dTo = to == null ? null : new Date(to.getMonthValue(), to.getDayOfMonth(), to.getYear());
		
		for (Transaction transaction : transactions) {
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
	
	@FXML
	private void clearDateFrom() {
		dateFrom.setValue(null);
		filter();
	}
	
	@FXML
	private void clearDateTo() {
		dateTo.setValue(null);
		filter();
	}
	
	@FXML
	private void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
