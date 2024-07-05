package service;

import java.util.List;

import dao.SegmentDao;
import exception.SegmentException;
import model.entities.Segment;
import util.DaoFactory;

public class SegmentService {
	
	private final SegmentDao segmentDao = DaoFactory.getDao(SegmentDao.class);
	
	public Segment include(Segment segment) {
		if (segment.getOrigin().equals(segment.getDestination())) {
			throw new SegmentException("Origin and destination must be distinct!");
		}
		return segmentDao.include(segment);
	}
	
	public Segment remove(int id) {
		Segment segment = retrieveSegmentById(id);
		if (segment == null) {
			throw new SegmentException("Cannot find a segment with id: " + id);
		}
		segmentDao.remove(id);
		return segment;
	}
	
	public Segment retrieveSegmentById(int id) {
		Segment segment = segmentDao.retrieveById(id);
		if (segment == null) {
			throw new SegmentException("There isn't a segment with id: " + id);
		}
		return segment;
	}

	public List<Segment> retrieveSegments() {
		return segmentDao.retrieveAll();
	}
	
	public Segment retrieveSegmentByOriginAndDestination(String origin, String destination) {
		return segmentDao.retrieveSegmentByOriginAndDestination(origin, destination);
	}
}
