package ua.project.spring.movie_theater.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import ua.project.spring.movie_theater.entities.MovieSession;

import static ua.project.spring.movie_theater.config.Constants.DEFAULT_SORT;
import static ua.project.spring.movie_theater.config.Constants.PAGE_SIZE;

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


    public static Sort getOrdersFromQueryParams(String sortField, String dir) {
        return dir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField.split(",")).ascending() :
                Sort.by(sortField.split(",")).descending();
    }

    public static Model setModelParams(Integer page, String sortParam, String sortDir, Model model, Page<MovieSession> tablePage, String keyword) {
        model.addAttribute("current_page", page);
        model.addAttribute("pages", tablePage.getTotalPages());
        model.addAttribute("rows", tablePage);
        model.addAttribute("sortParam", sortParam);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("revSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("search", keyword == null ? "" : keyword);
        return model;
    }

    public static String getUserNameFromSecurity() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
