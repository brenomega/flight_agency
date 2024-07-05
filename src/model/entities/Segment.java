package model.entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import util.Id;

public class Segment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String origin;
	private String destination;
	private double price;
	private double miles;
	
	private static final NumberFormat NF;
	
	static {
		NF = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		
		NF.setMaximumFractionDigits(2);
		NF.setMinimumFractionDigits(2);
	}
	
	public Segment() {
		
	}
	
	public Segment(String origin, String destination, Double price, Double miles) {
		this.origin = origin;
		this.destination = destination;
		this.price = price;
		this.miles = miles;
	}
	
	public int getId() {
		return id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPrice() {
		return NF.format(price);
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMiles() {
		return NF.format(miles);
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}

	@Override
	public String toString() {
		return "Segment [id=" + id + ", origin=" + origin + ", destination=" + destination + ", price=" + getPrice()
				+ ", miles=" + getMiles() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		return Objects.equals(destination, other.destination) && Objects.equals(origin, other.origin);
	}
}