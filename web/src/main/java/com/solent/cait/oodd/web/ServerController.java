package com.solent.cait.oodd.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.solent.cait.oodd.model.ShoppingService;
import com.solent.cait.oodd.model.UserBasket;
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.dto.Roles;
import com.solent.cait.oodd.dao.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.solent.cait.oodd.dto.Item;

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

    private User getSessionUser(HttpSession session) {
        //Get http user session and cast it to user type 
        User user = (User) session.getAttribute("sessionUser");
        if(user == null) {
            //If a user isn't logged om produce a blank user
            user = new User();
            user.setUsername("anonymous");
            user.setUserRole(Roles.ANONYMOUS);
            session.setAttribute("sessionUser",user);
            return user;
        } else {
            return user;
        }
    }
    
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
    public String srvhome(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        String message = "";
        String errorMessage = "";
        
        if (action == null) {
            // do nothing but show page
        } else if ("addItemToCart".equals(action)) {
            Item item = shoppingService.getItemByName(itemName);
            if (item == null) {
                message = "cannot add unknown " + itemName + " to cart";
            } else {
                message = "adding " + itemName + " to cart price= " + item.getPrice();
                userBasket.addItemToBasket(item);
            }
        } else if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            userBasket.removeItem(itemUuid);
        } else {
            message = "unknown action=" + action;
        }

        List<Item> availableItems = shoppingService.getAviliableItems();

        List<Item> baksetItems = userBasket.getCurrentBasketItems();

        Double totalPrice = userBasket.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", baksetItems);
        model.addAttribute("shoppingcartTotal", totalPrice);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "home";
    }
    
    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String aboutCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());
        
        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
    }

    @RequestMapping(value = "/contact", method = {RequestMethod.GET, RequestMethod.POST})
    public String contactCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);        
        model.addAttribute("currentUser", sessionUser.getUsername());

        
        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        return "contact";
    }
    
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        model.addAttribute("error", e.getMessage());
        return "error"; // default friendly exception message for sessionUser
    }


}
