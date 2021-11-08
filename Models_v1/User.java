package application;

import java.util.ArrayList;

public class User {
	private Budget budget;
	private ArrayList<PaymentSchedule> paymentSchedules;
	private ArrayList<Report> reports;
	private ArrayList<Transaction> transactions;
	
	public User() {
		super();
	}
	
	public User(Budget budget, ArrayList<PaymentSchedule> paymentSchedules
			, ArrayList<Report> reports, ArrayList<Transaction> transactions) {
		if (budget == null) throw new NullPointerException("budget is null");
		if (paymentSchedules == null) throw new NullPointerException("paymentSchedules is null");
		if (reports == null) throw new NullPointerException("reports is null");
		if (transactions == null) throw new NullPointerException("transactions is null");
		
		this.budget = budget;
		this.paymentSchedules = paymentSchedules;
		this.reports = reports;
		this.transactions = transactions;
	}
	
	public static User createDefaultUser() {
		Budget budget = new Budget(0, 0);
		ArrayList<PaymentSchedule> schedules = new ArrayList<>();
		ArrayList<Report> reports = new ArrayList<>();
		ArrayList<Transaction> transactions = new ArrayList<>();
		return new User(budget, schedules, reports, transactions);
	}
	
	public Budget getBudget() {
		return budget;
	}
	
	public ArrayList<PaymentSchedule> getPaymentSchedules() {
		return paymentSchedules;
	}
	
	public ArrayList<Report> getReports() {
		return reports;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(budget);
		sb.append("\n");
		sb.append(paymentSchedules);
		sb.append("\n");
		sb.append(reports);
		sb.append("\n");
		sb.append(transactions);
		
		return sb.toString();
	}
	
	public static User getExampleUser() {
		Budget b = Budget.getExampleBudget();
		ArrayList<PaymentSchedule> p = new ArrayList<>();
		p.add(PaymentSchedule.getExamplePaymentSchedule());
		
		ArrayList<Report> r = new ArrayList<>();
		r.add(Report.getExampleReport());
		
		ArrayList<Transaction> t = new ArrayList<>();
		t.add(Transaction.getExampleTransaction());
		
		return new User(b, p, r, t);
	}
	
	public static void main(String[] args) {
		User u = getExampleUser();
		System.out.println(u.getBudget() + "; Expected: 10.0 -- 0.0\n");
		System.out.println(u.getPaymentSchedules() + "; Expected: BofA -- 11-06-2021\n");
		System.out.println(u.getReports());
		System.out.println("Expected:\n10.0 -- 0.0\nTransactions:\nTarget -- San Jose\n"
				+ "Grocery -- 0.0\n11-06-2021\n10.0\nCategories:\nGrocery -- 0.0\n");
		System.out.println(u.getTransactions());
		System.out.println("Expected:\nTarget -- San Jose\nGrocery -- 0.0\n11-06-2021\n10.0\n");
		System.out.println(u.toString());
		System.out.println("Expected:10.0 -- 0.0\nBofA -- 11-06-2021\n10.0 -- 0.0\nTransactions:"
				+ "Target -- San Jose\nGrocery -- 0.0\n11-06-2021\n10.0\nCategories:\nGrocery -- 0.0"
				+ "Target -- San Jose\nGrocery -- 0.0\n11-06-2021\n10.0");
	}
}
