package ua.epam.project.spring.movie_theater.utils;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;

public class Utils {

    private Utils(){}

    public static Integer setValueToZeroIfNotProvidedOrNegative(Integer page) {
        if (page == null || page < 0) {
            page = 0;
        }
        return page;
    }

    public static Integer getPagesCount(double rowcount) {
        return (Integer) (int) Math.ceil(rowcount / PAGE_SIZE);
    }

    /**
     * Stores model attributes for pagination controls
     * @param page current page number
     * @param model Model object
     * @param rows table rows
     * @param pagesCount total pages
     * @param <K> Type of page
     * @param <M> Type of model
     * @param <T> Type of rows iterable container
     */
    public static<K extends Integer, M extends Model, T extends Iterable<?>> void setPagingAttributes(K page, M model, T rows, K pagesCount) {
        model.addAttribute("current_page", page);
        model.addAttribute("pages", pagesCount);
        model.addAttribute("rows", rows);
    }

    public static Sort getOrdersFromQueryParams(String sort) {
        return sort == null
                ? Sort.by("dayOfWeek", "timeStart")
                : Sort.by(sort.split(","));
    }
}
