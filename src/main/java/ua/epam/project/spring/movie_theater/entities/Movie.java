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
    private Set<MovieSession> sessions;

    public Set<MovieSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<MovieSession> sessions) {
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

    private Movie(MovieBuilder builder) {
       this.titleEn = builder.titleEn;
       this.titleUa = builder.titleUa;
       this.releaseYear = builder.releaseYear;
       this.runningTime = builder.runningTime;
       this.descriptionEn = builder.descriptionEn;
       this.descriptionUa = builder.descriptionUa;
       this.posterEn = builder.posterEn;
       this.posterUa = builder.posterUa;
    }

    public static MovieBuilder movieBuilder() {
        return new MovieBuilder();
    }

    public static class MovieBuilder {
        private String titleEn;
        private String titleUa;
        private int releaseYear;
        private int runningTime;
        private String descriptionUa;
        private String descriptionEn;
        private String posterUa;
        private String posterEn;

        public Movie build() {
            return new Movie(this);
        }

        public MovieBuilder titleEn(String titleEn) {
            this.titleEn = titleEn;
            return this;
        }

        public MovieBuilder titleUa(String titleUa) {
            this.titleUa = titleUa;
            return this;

        }

        public MovieBuilder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public MovieBuilder runningTime(int runningTime) {
            this.runningTime = runningTime;
            return this;
        }

        public MovieBuilder descriptionUa(String descriptionUa) {
            this.descriptionUa = descriptionUa;
            return this;
        }

        public MovieBuilder descriptionEn(String descriptionEn) {
            this.descriptionEn = descriptionEn;
            return this;
        }

        public MovieBuilder posterUa(String posterUa) {
            this.posterUa = posterUa;
            return this;
        }

        public MovieBuilder posterEn(String posterEn) {
            this.posterEn = posterEn;
            return this;
        }
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
