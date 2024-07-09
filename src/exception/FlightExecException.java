package exception;

public class FlightExecException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FlightExecException(String msg) {
		super(msg);
	}
}
