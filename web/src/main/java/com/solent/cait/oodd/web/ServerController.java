package com.solent.cait.oodd.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author caitlyn
 */

@Controller
@RequestMapping("/")
public class ServerController {
    
    @Autowired
    ShoppingService shoppingService = null;
    
    @Autowired 
    UserBasket userBasket = null;
    
    //Set up root of index
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvroot(Model model) {
        return "redirect:/index.html";
    }
    
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvindex(Model model) {
        return "redirect:/index.html";
    }
    
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvhome(Model model) {
        return "home";
    }
    



}
