class GrailsUIException extends Exception {
    public GrailsUIException() {
        super()
    }

    public GrailsUIException(String message) {
        super(message)
    }

    public GrailsUIException(Throwable throwable) {
        super(throwable)
    }

    public GrailsUIException(String message, Throwable throwable) {
        super(message, throwable)
    }
}