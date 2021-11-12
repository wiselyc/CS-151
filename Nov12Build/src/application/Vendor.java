package application;

public class Vendor {
	private String name;
	private String location;
	
	public Vendor() {
		super();
	}
	
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
}
