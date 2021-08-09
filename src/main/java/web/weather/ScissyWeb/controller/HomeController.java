package web.weather.ScissyWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public ModelAndView home(){
        return new ModelAndView("redirect:/weather");
    }

    @GetMapping(path ="/about")
    public String about(){
        return "about";
    }
}
