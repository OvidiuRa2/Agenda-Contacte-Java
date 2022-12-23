package agenda.exceptions;

/* @author radoi ovidiu*/
public class FormatGresitException extends RuntimeException{
    String data;

    public FormatGresitException(String data) {
        super("Data de nastere nu este in formatul ZZ.LL.AAAA");
        this.data =data;
    }
   
    
    
    
}
