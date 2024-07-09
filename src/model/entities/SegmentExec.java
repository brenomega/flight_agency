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
    private int flightExecId;

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
    
    public int getFlightExecId() {
    	return flightExecId;
    }
    
    public void setFlightExecId(int flightExecId) {
    	this.flightExecId = flightExecId;
    }

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("[").append("Id: ").append(id).append("/");
	    sb.append("Data/Hora de inicio: ").append(getInitialDateFormatted()).append("/");
    	sb.append("Data/Hora de fim: ").append(getFinalDateFormatted()).append("/");
	    sb.append("Trecho: ").append(getSegment()).append(" (Id da execução de voo: ");
	    sb.append(getFlightExecId()).append(")");
	    
	    sb.append("]");
	    return sb.toString();
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
