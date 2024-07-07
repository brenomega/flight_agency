package dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import dao.SegmentExecDao;
import model.entities.SegmentExec;

public class SegmentExecDaoImpl extends GenericDaoImpl<SegmentExec> implements SegmentExecDao {

	public List<SegmentExec> retrieveAllSegmentExecutionsWhereSegmentAppears(int id) {
        return map.values()
                .stream()
                .filter(segmentExec -> segmentExec.getSegment() != null && segmentExec.getSegment().getId() == id)
                .collect(Collectors.toList());
    }

	public SegmentExec retrieveSegmentExecutionByDateTimeAndSegment(String initialDate, String finalDate, int id) {
		return map.values()
				.stream()
				.filter(segmentExec -> segmentExec.getInitialDateFormatted().equals(initialDate) && segmentExec.getFinalDateFormatted().equals(finalDate) && segmentExec.getSegment().getId() == id)
				.findFirst()
				.orElse(null);
	}

	public List<SegmentExec> retrieveAllSegmentExecutionsBetweenDates(String initialDate, String finalDate) {
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		LocalDateTime initialDateTime = LocalDateTime.parse(initialDate + " 00:00", dateTimeFormatter);
        LocalDateTime finalDateTime = LocalDateTime.parse(finalDate + " 23:59", dateTimeFormatter);
		
		return map.values()
				.stream()
				.filter(segmentExec -> segmentExec.getInitialDate().isAfter(initialDateTime) && 
						segmentExec.getFinalDate().isBefore(finalDateTime))
				.collect(Collectors.toList());
	}
}
