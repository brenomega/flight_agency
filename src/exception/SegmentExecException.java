package exception;

public class SegmentExecException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SegmentExecException(String msg) {
		super(msg);
	}
}
