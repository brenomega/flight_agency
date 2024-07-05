package exception;

public class SegmentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SegmentException(String msg) {
		super(msg);
	}
}
