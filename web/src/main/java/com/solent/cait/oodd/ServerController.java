package com.solent.cait.oodd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author caitlyn
 */

@Controller
@RequestMapping("/")
public class ServerController {
    
    //Set up root of index
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvroot(Model model) {
        return "redirect:/index.html";
    }
    
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvindex(Model model) {
        return "index.html";
    }
}
