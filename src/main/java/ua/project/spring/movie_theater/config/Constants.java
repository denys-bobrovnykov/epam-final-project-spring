package ua.project.spring.movie_theater.config;


/**
 * Constants for all app
 */
public class Constants {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,10}$";
    public static final String FILE_REGEX = "^.*[\\w,\\s-]+\\.[A-Za-z]{3}$";
    public static final String DEFAULT_SORT = "dayOfSession,timeStart";
    public static final Integer PAGE_SIZE = 5;
}
