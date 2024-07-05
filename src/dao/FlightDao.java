package dao;

import java.util.List;

import model.entities.Flight;
import model.entities.Segment;

public interface FlightDao extends GenericDao<Flight> {
	List<Segment> retrieveAllFlightSegments(int id);
	Flight retrieveFlightByOriginDestinationAndSegments(String origin, String destination, List<Segment> segments);
	List<Flight> retrieveAllFlightsWhereSegmentAppears(int id);
}
