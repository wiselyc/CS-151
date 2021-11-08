package application;

public class Transaction {
	private Vendor vendor;
	private Category category;
	private Date date;
	private double amount;
	
	public Transaction(Vendor vendor, Category category, Date date, double amount) {
		if (vendor == null) throw new NullPointerException("vendor is null");
		if (category == null) throw new NullPointerException("category is null");
		if (date == null) throw new NullPointerException("date is null");
		if (amount < 0) throw new IllegalArgumentException("amount is negative");
		
		this.vendor = vendor;
		this.category = category;
		this.date = date;
		this.amount = amount;
	}
	
	public Vendor getVendor() {
		return vendor;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Date getDate() {
		return date;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vendor);
		sb.append("\n");
		sb.append(category);
		sb.append("\n");
		sb.append(date);
		sb.append("\n");
		sb.append(amount);
		
		return sb.toString();
	}
	
	public static Transaction getExampleTransaction() {
		return new Transaction(Vendor.getExampleVendor(), Category.getExampleCategory(),
				Date.getExampleDate(), 10);
	}
	
	public static void main(String[] args) {
		Transaction t = getExampleTransaction();
		System.out.println(t.getVendor() + "; Expected: Target -- San Jose\n");
		System.out.println(t.getCategory() + "; Expected: Grocery -- 0.0\n");
		System.out.println(t.getDate() + "; Expected: 11-06-2021\n");
		System.out.println(t.getAmount() + "; Expected: 10.0\n");
		System.out.println(t.toString());
		System.out.println("Expected:\nTarget -- San Jose\nGrocery -- 0.0\n11-06-2021\n10.0");
	}
}
