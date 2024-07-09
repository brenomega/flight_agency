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
	private int flightId;
	
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
	
	public double getDoublePrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMiles() {
		return NF.format(miles);
	}
	
	public double getDoubleMiles() {
		return miles;
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}
	
	public int getFlightId() {
		return flightId;
	}
	
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("[").append("Id: ").append(id).append("/");
	    sb.append("Origem: ").append(getOrigin()).append("/");
	    sb.append("Destino: ").append(getDestination()).append("/");
	    sb.append("Preço: ").append(getPrice()).append("/");
	    sb.append("Milhas: ").append(getMiles()).append("/");
	    sb.append("Id do voo: ").append(getFlightId());
	    
	    sb.append("]");
	    return sb.toString();
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