package chat_application_commonPart.Exception;


public abstract class InvalidDataException extends Exception {

	
	public static class MessageWithIdWasNotFound extends InvalidDataException{
		
	}
}
