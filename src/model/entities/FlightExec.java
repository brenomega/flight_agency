package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import util.Id;

public class FlightExec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    private int id;
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Flight flight;
    List<SegmentExec> segmentExecs;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public FlightExec() {
    	
    }
    
    public FlightExec(String initialDate, String finalDate) {
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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	public List<SegmentExec> getSegmentExecs() {
		return segmentExecs;
	}
	
	public void setSegmentExecs(List<SegmentExec> segmentExecs) {
		this.segmentExecs = segmentExecs;
	}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("{").append("Id: ").append(id).append("\n");
    	sb.append("Data/Hora de inicio: ").append(getInitialDateFormatted()).append("\n");
    	sb.append("Data/Hora de fim: ").append(getFinalDateFormatted()).append("\n");
    	sb.append("Voo: \n").append(getFlight()).append("}");

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
		FlightExec other = (FlightExec) obj;
		return Objects.equals(finalDate, other.finalDate) && Objects.equals(flight, other.flight)
				&& Objects.equals(initialDate, other.initialDate);
	}
}
