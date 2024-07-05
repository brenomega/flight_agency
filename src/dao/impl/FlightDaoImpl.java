package dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import dao.FlightDao;
import model.entities.Flight;
import model.entities.Segment;

public class FlightDaoImpl extends GenericDaoImpl<Flight> implements FlightDao {
	
	public List<Segment> retrieveAllFlightSegments(int id) {
		Flight flight = map.get(id);
		return flight.getSegments();
	}

	public Flight retrieveFlightByOriginDestinationAndSegments(String origin, String destination,
			List<Segment> segments) {
		return map.values()
	              .stream()
	              .filter(flight -> flight.getOrigin().equals(origin) 
	                     && flight.getDestination().equals(destination)
	                     && segmentsMatch(flight.getSegments(), segments))
	              .findFirst()
	              .orElse(null);
	}
		
	private boolean segmentsMatch(List<Segment> flightSegments, List<Segment> segments) {
	    if (flightSegments.size() != segments.size()) {
	        return false;
	    }
	    for (int i = 0; i < flightSegments.size(); i++) {
	        if (flightSegments.get(i).getId() != segments.get(i).getId()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public List<Flight> retrieveAllFlightsWhereSegmentAppears(int id) {
		return map.values()
	              .stream()
	              .filter(flight -> flight.getSegments().stream().anyMatch(segment -> segment.getId() == id))
	              .collect(Collectors.toList());
	}
}
