package application;

public class Vendor {
	private String name;
	private String location;
	
	public Vendor(String name, String location) {
		if (name == null) throw new NullPointerException("name is null");
		if (location == null) throw new NullPointerException("location is null");
		
		this.name = name;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String toString() {
		return name + " -- " + location;
	}
	
	public static Vendor getExampleVendor() {
		return new Vendor("Target", "San Jose");
	}
	
	public static void main(String[] args) {
		Vendor v = getExampleVendor();
		System.out.println(v.getName() + "; Expected: Target\n");
		System.out.println(v.getLocation() + "; Expected: San Jose\n");
		System.out.println(v.toString() + "; Expected: Target -- San Jose");
	}
}
