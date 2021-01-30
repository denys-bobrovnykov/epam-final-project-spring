package ua.epam.project.spring.movie_theater.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ua.epam.project.spring.movie_theater.config.Constants.FILE_REGEX;

public class MovieDTO {
    @Size(max = 36)
    private String titleUa;
    @Size(max = 36)
    private String titleEn;
    @Min(1900)@Max(9999)
    private int releaseYear;
    @Min(0)@Max(999)
    private int runningTime;
    @Size(max = 255)
    @Pattern(regexp = FILE_REGEX, message = "error.field.valid.file.poster.ua")
    private String poster;

    public MovieDTO(){}

    public String getTitleUa() {
        return titleUa;
    }

    public void setTitleUa(String titleUa) {
        this.titleUa = titleUa;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
