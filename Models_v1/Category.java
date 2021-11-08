package application;

public class Category {
	private String name;
	private double spending;
	
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
	
	public static Category getExampleCategory() {
		return new Category("Grocery", 0);
	}
	
	public static void main(String[] args) {
		Category c = getExampleCategory();
		System.out.println(c.getName() + "; Expected: Grocery\n");
		System.out.println(c.getSpending() + "; Expected: 0.0\n");
		c.addSpending(10);
		System.out.println(c.getSpending() + "; Expected: 10.0\n");
		System.out.println(c.toString() + "; Expected: Grocery -- 10.0");
	}
}
