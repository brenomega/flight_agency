package service;

import java.util.List;

import dao.FlightDao;
import dao.FlightExecDao;
import exception.FlightExecException;
import model.entities.Flight;
import model.entities.FlightExec;
import model.entities.SegmentExec;
import util.DaoFactory;

public class FlightExecService {

	private final FlightDao flightDao = DaoFactory.getDao(FlightDao.class);
	private final FlightExecDao flightExecDao = DaoFactory.getDao(FlightExecDao.class);
	

	public FlightExec include(FlightExec flightExec) {
		if (flightExec.getFlight() == null) {
			throw new FlightExecException("There is no flight to assign date!");
		}
		if (flightExec.getFinalDate().isBefore(flightExec.getInitialDate())) {
	        throw new FlightExecException("Final date cannot be before initial date!");
	    }
		return flightExecDao.include(flightExec);
	}
	
	public FlightExec remove(int id) {
		FlightExec flightExec = retrieveFlightExecById(id);
		if (flightExec == null) {
			throw new FlightExecException("Cannot find a flight execution with id: " + id);
		}
		flightExecDao.remove(id);
		return flightExec;
	}

	public FlightExec retrieveFlightExecById(int id) {
		FlightExec flightExec = flightExecDao.retrieveById(id);
		if (flightExec == null) {
			throw new FlightExecException("There isn't a flight execution with id: " + id);
		}
		return flightExec;
	}
	
	public List<FlightExec> retrieveFlightExecs() {
		return flightExecDao.retrieveAll();
	}
	
	public List<FlightExec> retrieveAllFlightExecutionsWhereFlightAppears(int id) {
		Flight flight = flightDao.retrieveById(id);
		if (flight == null) {
			throw new FlightExecException("Cannot find a flight with id: " + id);
		}
		return flightExecDao.retrieveAllFlightExecutionsWhereFlightAppears(id);
	}
	
	public FlightExec retrieveFlightExecutionByDateTimeAndFlight(String initialDate, String finalDate, int id) {
		return flightExecDao.retrieveFlightExecutionByDateTimeAndFlight(initialDate, finalDate, id);
	}
	
	public List<FlightExec> retrieveAllFlightExecutionsBetweenDates(String initialDate, String finalDate) {
		return flightExecDao.retrieveAllFlightExecutionsBetweenDates(initialDate, finalDate);
	}
	
	public List<SegmentExec> retrieveAllFlightExecSegmentExecs(int id) {
		FlightExec flightExec = retrieveFlightExecById(id);
		if (flightExec == null) {
			throw new FlightExecException("Cannot find a flight execution with id: " + id);
		}
		return flightExecDao.retrieveAllFlightExecSegmentExecs(id);
	}
}
