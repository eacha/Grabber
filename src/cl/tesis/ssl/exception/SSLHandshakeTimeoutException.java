package cl.tesis.ssl.exception;

public class SSLHandshakeTimeoutException extends Exception {

    public SSLHandshakeTimeoutException() {
    }

    public SSLHandshakeTimeoutException(String message) {
        super(message);
    }
}