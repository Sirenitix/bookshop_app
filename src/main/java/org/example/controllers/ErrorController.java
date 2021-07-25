package org.example.controllers;

import org.apache.log4j.Logger;
import org.example.exceptions.BookShelfLoginException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
public class ErrorController {
    private final Logger logger = Logger.getLogger(BookShelfController.class);

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }

    @GetMapping("/404")
    public String pnfFromBook() {
        logger.info("GET 404 returns 404.html");
        return "errors/404";
    }

    @GetMapping("/500")
    public String getfhFromBook() {
        logger.info("GET 500 returns 500.html");
        return "errors/500";
    }

    @PostMapping("/500")
    public String fhFromBook() {
        logger.info("GET 500 returns 500.html");
        return "errors/500";
    }

}
