package dao;

import java.util.List;

import model.entities.SegmentExec;

public interface SegmentExecDao extends GenericDao<SegmentExec> {
	List<SegmentExec> retrieveAllSegmentExecutionsWhereSegmentAppears(int id);
	SegmentExec retrieveSegmentExecutionByDateTimeAndSegment(String initialDate, String finalDate, int id);
	List<SegmentExec> retrieveAllSegmentExecutionsBetweenDates(String initialDate, String finalDate);
}
