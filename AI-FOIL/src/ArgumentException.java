/**
 * {@link ArgumentException} permet de gérer les arguments des exceptions non valides
 */
public class ArgumentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String argument;
	
	public ArgumentException(String s) {
		super("Argument non valide");
		argument = s;
	}
	
	public String getArgument() {
		return argument;
	}
}
