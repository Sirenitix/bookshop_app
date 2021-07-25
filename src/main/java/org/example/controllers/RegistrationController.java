package org.example.controllers;

import org.apache.log4j.Logger;
import org.example.entity.RegisForm;
import org.example.services.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping(value = "/newuser")
public class RegistrationController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final RegService regService;

    @Autowired
    public RegistrationController(RegService regService) {
        this.regService = regService;
    }

    @PostMapping
    public String error(Model model){
        logger.info("GET /newuser returns registration_page.html");
        model.addAttribute("regisForm", new RegisForm());
        return "registration_page";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("regisFrom") RegisForm regisForm, BindingResult bindingResult,Model model) {
        if(!regisForm.getPassword_first().equals(regisForm.getPassword_second())){
            model.addAttribute("regisForm", new RegisForm());
            model.addAttribute("passerror", true);
            return "registration_page";
        }
        if (bindingResult.hasErrors()) {
            return "registration_page";
        }
        regService.saveUser(regisForm);
        regService.setAut(regisForm);
        logger.debug("Welcome: " + regService.getAllUsers());

        return "redirect:/login";
    }

}