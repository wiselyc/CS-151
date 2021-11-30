package application;

public class Category {
	
	/** The name of the Category. Ex. Grocery, Snack, Books. */
	private String name;
	/** The current spending of this Category. */
	private double spending;
	
	/**
	 * This constructor should only be called by DbConnection.
	 */
	public Category() {
		super();
	}
	
	/**
	 * Construct a new Category with a specified name and spending.
	 * @param name the name of the Category
	 * @param spending the current spending of the Category
	 */
	public Category(String name, double spending) {
		if (name == null) throw new NullPointerException("name is null");
		if (spending < 0) throw new IllegalArgumentException("spending is negative");
		
		this.name = name;
		this.spending = spending;
	}
	
	/**
	 * Add spending to the Category.
	 * @param spending the spending to be added
	 */
	public void addSpending(double spending) {
		if (spending < 0) throw new IllegalArgumentException("spending is negative");
		
		this.spending += spending;
	}
	
	/**
	 * Get the name of the Category.
	 * @return the name of the Category.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the current spending of the Category.
	 * @return the current spending of the Category
	 */
	public double getSpending() {
		return spending;
	}
	
	/**
	 * Get the String representation of the Category.
	 * @return the String representation of the Category
	 */
	public String toString() {
		return name + " -- " + spending;
	}
}