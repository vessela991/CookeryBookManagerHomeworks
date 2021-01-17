package com.example.demo.internal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.exception.RecipeValidationException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.model.Recipe;

@ControllerAdvice
class HttpGlobalExceptionHandler {

    @ExceptionHandler(value = UserValidationException.class)
    public ModelAndView handleUserValidation(HttpServletRequest request, UserValidationException exception) {
        ModelAndView mv = new ModelAndView(request.getRequestURI());
        mv.addObject(Constants.ATTR_ERROR, exception.getMessage());
        mv.addObject(Constants.ATTR_TITTLE, exception.getMode());

        if (exception.getUserLoginModel() == null) {
            mv.addObject(Constants.ATTR_USER, exception.getUserRegisterModel());
        }
        if (exception.getUserRegisterModel() == null) {
            mv.addObject(Constants.ATTR_USER, exception.getUserLoginModel());
        }
        return mv;
    }

    @ExceptionHandler(value = RecipeValidationException.class)
    public ModelAndView handleRecipeValidation(HttpServletRequest request, RecipeValidationException exception) {
        ModelAndView mv = new ModelAndView();
        String requestPath = request.getRequestURI().replace("/", "");

        if (requestPath.equals("recipe-form")) {
            mv.setViewName(requestPath);
        }
        else {
            mv.setViewName("/");
        }

        mv.addObject(Constants.ATTR_ERROR, "system error");
        mv.addObject(Constants.ATTR_RECIPE, exception.getRecipe());
        return mv;
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(HttpServletRequest request, Exception exception) {
        request.getSession().invalidate();

        // ModelAndView mv = new ModelAndView(request.getRequestURI().replace("/", ""));
        // mv.addObject("error", exception.getMessage());
        // mv.addObject("recipe", new Recipe());
        // mv.addObject("loggedUser", request.getSession().getAttribute("user"));
        return Constants.REDIRECT;
    }
}