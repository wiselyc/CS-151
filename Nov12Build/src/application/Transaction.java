package application;

public class Transaction {
	private Vendor vendor;
	private String category;
	private Date date;
	private double amount;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Vendor vendor, String category, Date date, double amount) {
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
	
	public String getCategory() {
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
}
