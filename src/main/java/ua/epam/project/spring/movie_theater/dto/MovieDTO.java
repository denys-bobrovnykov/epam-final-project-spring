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
    private String descriptionUa;
    @Size(max = 255)
    private String descriptionEn;
    @Pattern(regexp = FILE_REGEX, message = "error.field.valid.file.poster.ua")
    private String posterUa;
    @Pattern(regexp = FILE_REGEX, message = "error.field.valid.file.poster.en")
    private String posterEn;

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

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getPosterUa() {
        return posterUa;
    }

    public void setPosterUa(String posterUa) {
        this.posterUa = posterUa;
    }

    public String getPosterEn() {
        return posterEn;
    }

    public void setPosterEn(String posterEn) {
        this.posterEn = posterEn;
    }

    public int getRunningTime() {
        return this.runningTime;
    }
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }


    @Override
    public String toString() {
        return "MovieDTO{" +
                ", titleUa='" + titleUa + '\'' +
                ", titleEn='" + titleEn + '\'' +
                ", releaseYear=" + releaseYear +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", posterUa='" + posterUa + '\'' +
                ", posterEn='" + posterEn + '\'' +
                '}';
    }
}
