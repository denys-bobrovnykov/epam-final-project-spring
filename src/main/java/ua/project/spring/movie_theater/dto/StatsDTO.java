package ua.project.spring.movie_theater.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Stats object DTO
 */
public class StatsDTO {
    @NotNull(message = "must.be.present.start.date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayStart;
    @NotNull(message = "must.be.present.end.date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayEnd;

    public LocalDate getDayStart() {
        return dayStart;
    }

    public void setDayStart(LocalDate dayStart) {
        this.dayStart = dayStart;
    }

    public LocalDate getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(LocalDate dayEnd) {
        this.dayEnd = dayEnd;
    }
}
