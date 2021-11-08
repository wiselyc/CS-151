package application;

import java.util.ArrayList;

public class Report {
	private Budget budget;
	private ArrayList<Transaction> transactions;
	private ArrayList<Category> categories;
	
	public Report(Budget budget, ArrayList<Transaction> transactions, ArrayList<Category> categories) {
		if (budget == null) throw new NullPointerException("budget is null");
		if (transactions == null) throw new NullPointerException("transactions is null");
		if (categories == null) throw new NullPointerException("categories is null");
		
		this.budget = budget;
		this.transactions = transactions;
		this.categories = categories;
	}
	
	public Budget getBudget() {
		return budget;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(budget);
		
		sb.append("\nTransactions:\n");
		for (Transaction t : transactions) {
			sb.append(t);
			sb.append("\n");
		}
		
		sb.append("Categories:\n");
		for (Category c : categories) {
			sb.append(c);
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static Report getExampleReport() {
		Budget b = Budget.getExampleBudget();
		
		ArrayList<Transaction> t = new ArrayList<>();
		t.add(Transaction.getExampleTransaction());
		
		ArrayList<Category> c = new ArrayList<>();
		c.add(Category.getExampleCategory());
		
		return new Report(b, t, c);
	}
	
	public static void main(String[] args) {
		Report r = getExampleReport();
		System.out.println(r.getBudget() + "; Expected: 10.0 -- 0.0\n");
		System.out.println(r.getTransactions());
		System.out.println("Expected:\nTarget -- San Jose\nGrocery -- 0.0\n11-06-2021\n10.0\n");
		System.out.println(r.getCategories() + "; Expected: Grocery -- 10.0\n");
		System.out.println(r.toString());
		System.out.println("Expected:\n10.0 -- 0.0\nTransactions:\nTarget -- San Jose\nGrocery -- 0.0\n"
				+ "11-06-2021\n10.0\nCategories:\nGrocery -- 0.0");
	}
}
