package controller;

import main.java.itu.annotation.Controller;
import main.java.itu.annotation.UrlMapping;
import main.java.core.ModelAndView;


@Controller 
public class PatientController {
    @UrlMapping(url="/patient", methodeHttp="GET")
    
    public ModelAndView patient(){
        ModelAndView mv = new ModelAndView("patient");
        return mv;
    }
    
}
