package ua.project.spring.movie_theater.entities;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Movie entity
 */
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
    private String poster;
    @OneToMany(mappedBy = "movie")
    private Set<MovieSession> sessions;

    public Movie() {
    }

    public Movie(String titleUa, String titleEn, int releaseYear) {
        this.titleUa = titleUa;
        this.titleEn = titleEn;
        this.releaseYear = releaseYear;
    }

    private Movie(MovieBuilder builder) {
        this.titleEn = builder.titleEn;
        this.titleUa = builder.titleUa;
        this.releaseYear = builder.releaseYear;
        this.runningTime = builder.runningTime;
        this.poster = builder.poster;
    }

    public static MovieBuilder movieBuilder() {
        return new MovieBuilder();
    }

    public Set<MovieSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<MovieSession> sessions) {
        this.sessions = sessions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public static class MovieBuilder {
        private String titleEn;
        private String titleUa;
        private int releaseYear;
        private int runningTime;
        private String poster;

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


        public MovieBuilder poster(String poster) {
            this.poster = poster;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (releaseYear != movie.releaseYear) return false;
        if (!Objects.equals(titleEn, movie.titleEn)) return false;
        return Objects.equals(titleUa, movie.titleUa);
    }

    @Override
    public int hashCode() {
        int result = titleEn != null ? titleEn.hashCode() : 0;
        result = 31 * result + (titleUa != null ? titleUa.hashCode() : 0);
        result = 31 * result + releaseYear;
        return result;
    }
}
