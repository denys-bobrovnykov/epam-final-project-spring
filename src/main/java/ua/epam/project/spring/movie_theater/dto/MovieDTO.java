package ua.epam.project.spring.movie_theater.dto;


public class MovieDTO {
    private String titleUa;
    private String titleEn;
    private int releaseYear;
    private int runningTime;
    private String descriptionUa;
    private String descriptionEn;
    private String posterUa;
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
