package ua.epam.project.spring.movie_theater.utils;

import org.springframework.ui.Model;
import ua.epam.project.spring.movie_theater.dbview.IndexTableRow;

import java.util.List;

import static ua.epam.project.spring.movie_theater.config.Constants.PAGE_SIZE;

public class Utils {

    private Utils(){}

    public static Integer setValueToZeroIfNotProvidedOrNegative(Integer page) {
        if (page == null || page < 0) {
            page = 0;
        }
        return page;
    }

    public static void setPagingAttributes(Integer page, Model model, List<IndexTableRow> rows, Integer pagesCount) {
        model.addAttribute("current_page", page);
        model.addAttribute("pages", pagesCount);
        model.addAttribute("rows", rows);
    }

    public static Integer getPagesCount(double rowcount) {
        Integer pagesCount = (int) Math.ceil(rowcount / PAGE_SIZE);
        return pagesCount;
    }


}
