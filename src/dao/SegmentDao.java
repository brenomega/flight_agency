package dao;


import model.entities.Segment;

public interface SegmentDao extends GenericDao<Segment> {
	Segment retrieveSegmentByOriginAndDestination(String origin, String destination);
}
