package agenda.exceptions;

/* @author radoi ovidiu*/
public class NumeInvalidException extends RuntimeException {
    String nume;

    public NumeInvalidException(String nume) {
        this.nume=nume;
    }
    
    public NumeInvalidException(String nume, String message) {
        super(message);
        this.nume=nume;
        
    }
    
    
}
