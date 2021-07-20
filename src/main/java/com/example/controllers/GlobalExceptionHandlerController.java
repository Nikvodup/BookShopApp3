package com.example.controllers;

import com.example.errors.EmptySearchException;
import com.example.security.exceptions.WrongEmailException;
import com.example.security.exceptions.WrongPhoneException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes){
        Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }

    @ExceptionHandler(WrongEmailException.class)
    public String handleWrongEmailException(WrongEmailException exception, RedirectAttributes redirectAttributes){
        Logger.getLogger(this.getClass().getSimpleName()).warning(exception.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("wrongEmail", exception);
        return "redirect:/signup";
    }

    @ExceptionHandler(WrongPhoneException.class)
    public String handleWrongPhoneException(WrongPhoneException exception, RedirectAttributes redirectAttributes){
        Logger.getLogger(this.getClass().getSimpleName()).warning(exception.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("wrongPhone", exception);
        return "redirect:/signup";
    }

}

