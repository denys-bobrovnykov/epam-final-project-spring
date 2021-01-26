package ua.epam.project.spring.movie_theater.entities;



import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title_en", nullable = false)
    private String titleEn;
    @Column(name = "title_ua", nullable = false)
    private String titleUa;
    @Column(name = "release_year", nullable = false)
    private int releaseYear;
    @Column(name = "running_time", nullable = false)
    private int runningTime;
    private String descriptionUa;
    private String descriptionEn;
    private String posterUa;
    private String posterEn;
    @OneToMany(mappedBy = "movie")
    private Set<Session> sessions;

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Movie(){}

    public Movie(String titleUa, String titleEn, int releaseYear) {
        this.titleUa = titleUa;
        this.titleEn = titleEn;
        this.releaseYear = releaseYear;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", titleUa='" + titleUa + '\'' +
                ", titleEn='" + titleEn + '\'' +
                ", releaseYear=" + releaseYear +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", posterUa='" + posterUa + '\'' +
                ", posterEn='" + posterEn + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
