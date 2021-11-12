package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SummaryController {
	private DBManager manager = DBManager.getInstance();
	private User user = manager.getUser();
	
	@FXML PieChart pieChart;
	@FXML Button menuButton;
	
	@FXML
	public void initialize() {
		if (user.getCategories().isEmpty()) {
			pieChart.setTitle("Please input a new transaction");
		}
		
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		double totalSpending = 0;
		
		for (Category category : user.getCategories().values()) {
			totalSpending += category.getSpending();
		}
		
		for (Category category : user.getCategories().values()) {
			double temp = category.getSpending() / totalSpending;
			String str = category.getName() + ", $" + category.getSpending() + ", " + toPercentage(temp);
			list.add(new PieChart.Data(str, category.getSpending()));
		}
		
		for (PieChart.Data data : list) {
			pieChart.getData().add(data);
		}
		
		pieChart.setLegendVisible(false);
		System.out.println("loaded categories");
	}
	
	private String toPercentage(double number) {
		int temp = (int) Math.round(number * 100);
		return String.valueOf(temp) + "%";
	}
	
	public void switchToMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) menuButton.getScene().getWindow();
		stage.setScene(scene);
	}
}
