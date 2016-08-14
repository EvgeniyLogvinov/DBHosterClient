package pt.DBHosterClient;

public class ClientException extends Exception {
	String message = null;
    
	public ClientException(String message) {
    	super(message);
    	this.message = message;
    }
    
    public String getMessage() {
    	return message;
    }
}
