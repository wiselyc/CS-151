package application;

public class Date {
	private static final String[] monthStr = new String[] {"January", "Febuary", "March", "April"
			, "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private int month;
	private int day;
	private int year;
	
	public Date() {
		super();
	}
	
	public Date(int month, int day, int year) {
		if (month < 1 || month > 12) throw new IllegalArgumentException("month is invalid");
		if (day < 1 || day > 31) throw new IllegalArgumentException("day is invalid");
		if (year < 0) throw new IllegalArgumentException("year is invalid");
		if (month == 2) {
			if (isLeapYear(year)) {
				if (day > 29) throw new IllegalArgumentException("day > 29");
			} else {
				if (day > 28) throw new IllegalArgumentException("day > 28");
			}
		}
		
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public String getMonthStr(boolean monthAbbreviated) {
		return monthAbbreviated ? monthStr[month - 1].substring(0, 3) : monthStr[month - 1];
	}
	
	public int getDay() {
		return day;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getDate() {
		return toString();
	}
	
	public String getDateStr(boolean monthAbbreviated) {
		return toStringStr(monthAbbreviated);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (month < 10) {
			sb.append('0');
			sb.append(month);
		} else {
			sb.append(month);
		}
		sb.append('/');
		
		if (day < 10) {
			sb.append('0');
			sb.append(day);
		} else {
			sb.append(day);
		}
		sb.append('/');
		
		sb.append(year);
		
		return sb.toString();
	}
	
	public String toStringStr(boolean monthAbbreviated) {
		StringBuilder sb = new StringBuilder();
		sb.append(getMonthStr(monthAbbreviated));
		sb.append(' ');
		sb.append(day);
		sb.append(", ");
		sb.append(year);
		
		return sb.toString();
	}
	
	public int compareTo(Date other) {
		if (year != other.year)
			return year - other.year;
		if (month != other.month)
			return month - other.month;
		return day - other.day;
	}
	
	private boolean isLeapYear(int year) {
		return java.time.Year.of(year).isLeap();
	}
}
