package scanner.prototype.exception;

public enum ExceptionMessage {
    ERROR("Error.");

    private final String exception;

    private ExceptionMessage(String e) { 
        this.exception = e; 
    }

    public String getExceptionMessage() { 
        return exception; 
    }
}
