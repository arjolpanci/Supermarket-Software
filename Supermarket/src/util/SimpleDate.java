package util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SimpleDate implements Serializable{
	
	private int day;
	private int month;
	private int year;
	
	public SimpleDate(int day, int month, int year) {
		this.day = day;
		this .month = month;
		this.year = year;
	}
	
	public SimpleDate(LocalDate date) {
		this.day = date.getDayOfMonth();
		this.month = date.getMonthValue();
		this.year = date.getYear();
	}
	
	public SimpleDate(LocalDateTime date) {
		this.day = date.getDayOfMonth();
		this.month = date.getMonthValue();
		this.year = date.getYear();
	}
	
	public boolean isBetween(LocalDate from, LocalDate to) {
		boolean flag = false;
		LocalDate date = this.toLocalDate();
		while(true) {
			if(from.equals(date)) {
				flag = true;
				break;
			}
			if(from.getDayOfMonth() == to.getDayOfMonth() && from.getMonthValue() == to.getMonthValue() 
					&& from.getYear() == to.getYear()) break;
			from = from.plusDays(1);
		}
		return flag;
	}
	
	public LocalDate toLocalDate() {
		LocalDate d = LocalDate.of(year, month, day);
		return d;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}

}
