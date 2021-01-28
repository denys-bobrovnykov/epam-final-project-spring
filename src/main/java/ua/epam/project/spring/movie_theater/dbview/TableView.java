package ua.epam.project.spring.movie_theater.dbview;


import java.time.LocalDate;
import java.time.LocalTime;

public class TableView {
    private int id;
    private LocalDate dayOfWeek;
    private LocalTime timeStart;
    private String titleEn;
    private String titleUa;
    private int seatsCount;

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleUa() {
        return titleUa;
    }

    public void setTitleUa(String titleUa) {
        this.titleUa = titleUa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(LocalDate dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }
}
