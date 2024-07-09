package service;

import java.util.List;

import dao.SegmentDao;
import dao.SegmentExecDao;
import exception.SegmentExecException;
import model.entities.Segment;
import model.entities.SegmentExec;
import util.DaoFactory;

public class SegmentExecService {
	
	private final SegmentDao segmentDao = DaoFactory.getDao(SegmentDao.class);
	private final SegmentExecDao segmentExecDao = DaoFactory.getDao(SegmentExecDao.class);
	
	public SegmentExec include(SegmentExec segmentExec) {
		if (segmentExec.getSegment() == null) {
			throw new SegmentExecException("There is no segment to assign date!");
		}
		if (segmentExec.getFinalDate().isBefore(segmentExec.getInitialDate())) {
	        throw new SegmentExecException("Final date cannot be before initial date!");
	    }
		return segmentExecDao.include(segmentExec);
	}
	
	public SegmentExec remove(int id) {
		SegmentExec segmentExec = retrieveSegmentExecById(id);
		if (segmentExec == null) {
			throw new SegmentExecException("Cannot find a segment execution with id: " + id);
		}
		segmentExecDao.remove(id);
		return segmentExec;
	}
	
	public SegmentExec retrieveSegmentExecById(int id) {
		SegmentExec segmentExec = segmentExecDao.retrieveById(id);
		if (segmentExec == null) {
			throw new SegmentExecException("There isn't a segment execution with id: " + id);
		}
		return segmentExec;
	}
	
	public List<SegmentExec> retrieveSegmentExecs() {
		return segmentExecDao.retrieveAll();
	}
	
	public List<SegmentExec> retrieveAllSegmentExecutionsWhereSegmentAppears(int id) {
		Segment segment = segmentDao.retrieveById(id);
		if (segment == null) {
			throw new SegmentExecException("Cannot find a segment with id: " + id);
		}
		return segmentExecDao.retrieveAllSegmentExecutionsWhereSegmentAppears(id);
	}
	
	public SegmentExec retrieveSegmentExecutionByDateTimeAndSegment(String initialDate, String finalDate, int id) {
		return segmentExecDao.retrieveSegmentExecutionByDateTimeAndSegment(initialDate, finalDate, id);
	}
	
	public List<SegmentExec> retrieveAllSegmentExecutionsBetweenDates(String initialDate, String finalDate) {
		return segmentExecDao.retrieveAllSegmentExecutionsBetweenDates(initialDate, finalDate);
	}
}
