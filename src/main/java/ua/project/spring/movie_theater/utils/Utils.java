package ua.project.spring.movie_theater.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import ua.project.spring.movie_theater.entities.MovieSession;

public class Utils {

    private Utils(){}

    public static Integer setValueToZeroIfNotProvidedOrNegative(Integer page) {
        if (page == null || page < 0) {
            page = 0;
        }
        return page;
    }

    public static Sort getOrdersFromQueryParams(String sortField, String dir) {
        return dir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField.split(",")).ascending() :
                Sort.by(sortField.split(",")).descending();
    }

    public static Model setModelParams(Integer page, String sortParam, String sortDir, Model model, Page<MovieSession> tablePage, String keyword, String value) {
        model.addAttribute("current_page", page);
        model.addAttribute("pages", tablePage.getTotalPages());
        model.addAttribute("rows", tablePage);
        model.addAttribute("sortParam", sortParam);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("revSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("search", keyword == null ? "" : keyword);
        model.addAttribute("value", value == null ? "" : value);
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
