package model.entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import util.Id;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private double price;
	private double miles;
	private List<SegmentExec> segmentExecs;
	private Customer customer;
	
	public Ticket() {
		
	}
	
	public Ticket(Customer customer, List<SegmentExec> segmentExecs) {
		this.customer = customer;
		this.segmentExecs = segmentExecs;
		
		for (SegmentExec segmentExec : segmentExecs) {
			price += segmentExec.getSegment().getDoublePrice();
			miles += segmentExec.getSegment().getDoubleMiles();
		}
	}
	
	private static final NumberFormat NF;
	
	static {
		NF = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		
		NF.setMaximumFractionDigits(2);
		NF.setMinimumFractionDigits(2);
	}

	public int getId() {
		return id;
	}

	public String getPrice() {
		return NF.format(price);
	}

	public String getMiles() {
		return NF.format(miles);
	}
	
	public double getDoubleMiles() {
		return miles;
	}

	public List<SegmentExec> getSegmentExecs() {
		return segmentExecs;
	}

	public Customer getCustomer() {
		return customer;
	}
}
