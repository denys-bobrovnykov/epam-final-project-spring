package ua.epam.project.spring.movie_theater.exceptions;

public class DBexception extends Exception {

    public DBexception(String message, Throwable cause) {
        super(message, cause);
    }

    public DBexception(String message) {
        super(message);
    }

    public DBexception(Throwable cause) {
        super(cause);
    }
}
