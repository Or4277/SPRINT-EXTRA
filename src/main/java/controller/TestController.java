package controller;

import main.java.itu.annotation.Controller;
import main.java.itu.annotation.UrlMapping;
import main.java.core.ModelAndView;


@Controller 
public class TestController {
    @UrlMapping(url="/test", methodeHttp="GET")
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView("test");
        return mv;
    }
    
}
