package application;

public class Budget {
	private double budget;
	private double spending;
	
	public Budget(double budget, double spending) {
		if (budget < 0) budget = 0;
		if (spending < 0) spending = 0;
		
		this.budget = budget;
		this.spending = spending;
	}
	
	public void updateBudget(double budget) {
		if (budget < 0) return;
		
		this.budget = budget;
	}
	
	public void addSpending(double spending) {
		if (spending < 0) return;
		
		this.spending += spending;
	}
	
	public double getBudget() {
		return budget;
	}
	
	public double getSpending() {
		return spending;
	}
	
	public String toString() {
		return budget + " -- " + spending;
	}
	
	public static Budget getExampleBudget() {
		return new Budget(10, 0);
	}
	
	public static void main(String[] args) {
		Budget b = getExampleBudget();
		System.out.println(b.getBudget() + "; Expected: 10.0\n");
		System.out.println(b.getSpending() + "; Expected: 0.0\n");
		b.updateBudget(100);
		System.out.println(b.getBudget() + "; Expected: 100.0\n");
		b.addSpending(10);
		System.out.println(b.getSpending() + "; Expected: 10.0\n");
		System.out.println(b.toString() + "; Expected: 100.0 -- 10.0");
	}
}
