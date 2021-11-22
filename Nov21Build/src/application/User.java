package application;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private ArrayList<Transaction> transactions;
	private HashMap<String, Category> categories;
	
	public User() {
		super();
	}
	
	public User(ArrayList<Transaction> transactions, HashMap<String, Category> categories) {
		if (transactions == null) throw new NullPointerException("transactions is null");
		if (categories == null) throw new NullPointerException("categories is null");
		
		this.transactions = transactions;
		this.categories = categories;
	}
	
	public static User createDefaultUser() {
		// create a new user object to store the data
		ArrayList<Transaction> transactions = new ArrayList<>();
		HashMap<String, Category> categories = new HashMap<>();
		return new User(transactions, categories);
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void addToCategory(String name, double amount) {
		if (!categories.containsKey(name)) {
			createCategory(name);
		}
		
		categories.get(name).addSpending(amount);
	}
	
	public boolean createCategory(String name) {
		if (categories.containsKey(name)) return false;
		
		Category temp = new Category(name, 0);
		categories.put(name, temp);
		
		return categories.containsKey(name);
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public HashMap<String, Category> getCategories() {
		return categories;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(transactions);
		sb.append(categories);
		
		return sb.toString();
	}
}
