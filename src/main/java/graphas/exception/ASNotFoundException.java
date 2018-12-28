package graphas.exception;

public class ASNotFoundException extends RuntimeException {

	public ASNotFoundException(Long id) {
		super("Could not find Autonomous system with ID:  " + id);
	}
}
