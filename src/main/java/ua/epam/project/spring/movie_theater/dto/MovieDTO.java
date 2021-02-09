package ua.epam.project.spring.movie_theater.dto;


import javax.validation.constraints.*;

import static ua.epam.project.spring.movie_theater.config.Constants.FILE_REGEX;

public class MovieDTO {
    @NotBlank(message = "error.empty.title.ua")
    @Size(max = 36, message = "title.max")
    private String titleUa;
    @NotBlank(message = "error.empty.title.en")
    private String titleEn;
    @NotNull(message = "error.empty.release.year")
    private int releaseYear;
    @NotNull(message = "error.empty.release.year")
    private int runningTime;
    @Pattern(regexp = FILE_REGEX, message = "error.field.valid.file.poster")
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
