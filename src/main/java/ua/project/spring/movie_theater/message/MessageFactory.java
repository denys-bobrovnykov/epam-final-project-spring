package ua.project.spring.movie_theater.message;

public class MessageFactory {

    private MessageFactory(){}

    public static Message getMessage(String text) {
        return () -> text;
    }
}
