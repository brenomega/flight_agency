package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.Id;

public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String origin;
	private String destination;
	private List<Segment> segments;
	
	public Flight() {
		
	}
	
	public Flight(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
		this.segments = new ArrayList<>();
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
	
	public List<Segment> getSegments() {
		return segments;
	}
	
	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", origin=" + origin + ", destination=" + destination + ", segments=" + segments
				+ "]";
	}
}