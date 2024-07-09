package dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import dao.FlightExecDao;
import model.entities.FlightExec;
import model.entities.SegmentExec;

public class FlightExecDaoImpl extends GenericDaoImpl<FlightExec> implements FlightExecDao {

	public List<FlightExec> retrieveAllFlightExecutionsWhereFlightAppears(int id) {
		return map.values()
                .stream()
                .filter(flightExec -> flightExec.getFlight() != null && flightExec.getFlight().getId() == id)
                .collect(Collectors.toList());
	}

	public FlightExec retrieveFlightExecutionByDateTimeAndFlight(String initialDate, String finalDate, int id) {
		return map.values()
				.stream()
				.filter(flightExec -> flightExec.getInitialDateFormatted().equals(initialDate) && flightExec.getFinalDateFormatted().equals(finalDate) && flightExec.getFlight().getId() == id)
				.findFirst()
				.orElse(null);
	}

	public List<FlightExec> retrieveAllFlightExecutionsBetweenDates(String initialDate, String finalDate) {
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		LocalDateTime initialDateTime = LocalDateTime.parse(initialDate + " 00:00", dateTimeFormatter);
        LocalDateTime finalDateTime = LocalDateTime.parse(finalDate + " 23:59", dateTimeFormatter);
		
		return map.values()
				.stream()
				.filter(flightExec -> flightExec.getInitialDate().isAfter(initialDateTime) && 
						flightExec.getFinalDate().isBefore(finalDateTime))
				.collect(Collectors.toList());
	}
	
	public List<SegmentExec> retrieveAllFlightExecSegmentExecs(int id) {
		FlightExec flightExec = map.get(id);
		return flightExec.getSegmentExecs();
	}
}
