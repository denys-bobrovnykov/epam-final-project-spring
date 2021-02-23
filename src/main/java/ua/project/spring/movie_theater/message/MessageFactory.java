package ua.project.spring.movie_theater.message;

/**
 * Message class wrapper for string messages
 */
public class MessageFactory {

    private MessageFactory(){}

    public static Message getMessage(String text) {
        return () -> text;
    }
}
