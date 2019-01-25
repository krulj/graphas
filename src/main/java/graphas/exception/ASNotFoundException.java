package graphas.exception;

public class ASNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ASNotFoundException(Long id) {
		super("Could not find Autonomous system with ID:  " + id);
	}
	
	public ASNotFoundException(String name) {
		super("Could not find Autonomous system with Name:  " + name);
	}
}
