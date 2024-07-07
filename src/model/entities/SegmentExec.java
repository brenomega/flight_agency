package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import util.Id;

public class SegmentExec implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Segment segment;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public SegmentExec() {

    }

    public SegmentExec(String initialDate, String finalDate) {
        this.initialDate = LocalDateTime.parse(initialDate, formatter);
        this.finalDate = LocalDateTime.parse(finalDate, formatter);
    }

    public int getId() {
        return id;
    }
    
    public LocalDateTime getInitialDate() {
    	return initialDate;
    }

    public String getInitialDateFormatted() {
        return initialDate.format(formatter);
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = LocalDateTime.parse(initialDate, formatter);
    }
    
    public LocalDateTime getFinalDate() {
    	return finalDate;
    }

    public String getFinalDateFormatted() {
        return finalDate.format(formatter);
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = LocalDateTime.parse(finalDate, formatter);
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

	@Override
	public String toString() {
		return "SegmentExec [id=" + id + ", initialDate=" + getInitialDateFormatted() + ", finalDate=" + getFinalDateFormatted() + ", segment="
				+ getSegment() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SegmentExec other = (SegmentExec) obj;
		return Objects.equals(finalDate, other.finalDate) && Objects.equals(initialDate, other.initialDate)
				&& Objects.equals(segment, other.segment);
	}
}
