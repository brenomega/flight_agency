package dao;

import java.util.List;

import model.entities.FlightExec;
import model.entities.SegmentExec;

public interface FlightExecDao extends GenericDao<FlightExec> {
	List<FlightExec> retrieveAllFlightExecutionsWhereFlightAppears(int id);
	FlightExec retrieveFlightExecutionByDateTimeAndFlight(String initialDate, String finalDate, int id);
	List<FlightExec> retrieveAllFlightExecutionsBetweenDates(String initialDate, String finalDate);
	List<SegmentExec> retrieveAllFlightExecSegmentExecs(int id);
}
