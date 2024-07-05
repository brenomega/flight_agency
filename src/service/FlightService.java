package service;

import java.util.List;

import dao.FlightDao;
import dao.SegmentDao;
import exception.FlightException;
import exception.SegmentException;
import model.entities.Flight;
import model.entities.Segment;
import util.DaoFactory;

public class FlightService {

	private final FlightDao flightDao = DaoFactory.getDao(FlightDao.class);
	private final SegmentDao segmentDao = DaoFactory.getDao(SegmentDao.class);
	
	public Flight include(Flight flight) {
		if (flight.getOrigin().equals(flight.getDestination())) {
			throw new FlightException("Origin and destination must be distinct!");
		}
		return flightDao.include(flight);
	}
	
	public Flight remove(int id) {
		Flight flight = retrieveFlightById(id);
		if (flight == null) {
			throw new FlightException("Cannot find a flight with id: " + id);
		}
		flightDao.remove(id);
		return flight;
	}
	
	public Flight retrieveFlightById(int id) {
		Flight flight = flightDao.retrieveById(id);
		if (flight == null) {
			throw new FlightException("There isn't a flight with id: " + id);
		}
		return flight;
	}
	
	private Segment retrieveSegmentById(int id) {
		Segment segment = segmentDao.retrieveById(id);
		if (segment == null) {
			throw new SegmentException("There isn't a segment with id: " + id);
		}
		return segment;
	}
	
	public List<Flight> retrieveFlights() {
		return flightDao.retrieveAll();
	}
	
	public List<Segment> retrieveAllFlightSegments(int id) {
		Flight flight = retrieveFlightById(id);
		if (flight == null) {
			throw new FlightException("Cannot find a flight with id: " + id);
		}
		return flightDao.retrieveAllFlightSegments(id);
	}
	
	public Flight retrieveFlightByOriginDestinationAndSegments(String origin, String destination,
			List<Segment> segments) {
		return flightDao.retrieveFlightByOriginDestinationAndSegments(origin, destination, segments);
	}
	
	public List<Flight> retrieveAllFlightsWhereSegmentAppears(int id) {
		Segment segment = retrieveSegmentById(id);
		if (segment == null) {
			throw new SegmentException("Cannot find a segment with id: " + id);
		}
		return flightDao.retrieveAllFlightsWhereSegmentAppears(id);
	}
}
