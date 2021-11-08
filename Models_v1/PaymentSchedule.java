package application;

public class PaymentSchedule {
	private String name;
	private Date date;
	
	public PaymentSchedule(String name, Date date) {
		if (name == null) throw new NullPointerException("name is null");
		if (date == null) throw new NullPointerException("date is null");
		
		this.name = name;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String toString() {
		return name + " -- " + date;
	}
	
	public static PaymentSchedule getExamplePaymentSchedule() {
		return new PaymentSchedule("BofA", Date.getExampleDate());
	}
	
	public static void main(String[] args) {
		PaymentSchedule p = getExamplePaymentSchedule();
		System.out.println(p.getName() + "; Expected: BofA\n");
		System.out.println(p.getDate() + "; Expected: 11-06-2021\n");
		System.out.println(p.toString() + "; Expected: BofA -- 11-06-2021\n");
	}
}
