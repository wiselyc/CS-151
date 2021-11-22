package application;

public class Category {
	private String name;
	private double spending;
	
	public Category() {
		super();
	}
	
	public Category(String name, double spending) {
		if (name == null) throw new NullPointerException("name is null");
		if (spending < 0) throw new IllegalArgumentException("spending is negative");
		
		this.name = name;
		this.spending = spending;
	}
	
	public void addSpending(double spending) {
		if (spending < 0) throw new IllegalArgumentException("spending is negative");
		
		this.spending += spending;
	}
	
	public String getName() {
		return name;
	}
	
	public double getSpending() {
		return spending;
	}
	
	public String toString() {
		return name + " -- " + spending;
	}
}