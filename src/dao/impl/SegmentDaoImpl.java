package dao.impl;

import dao.SegmentDao;
import model.entities.Segment;

public class SegmentDaoImpl extends GenericDaoImpl<Segment> implements SegmentDao {

	public Segment retrieveSegmentByOriginAndDestination(String origin, String destination) {
		return map.values()
	              .stream()
	              .filter(segment -> segment.getOrigin().equals(origin) && segment.getDestination().equals(destination))
	              .findFirst()
	              .orElse(null);
	}
}
