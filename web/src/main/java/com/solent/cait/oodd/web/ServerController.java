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
    
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String AddNewItem(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());
        
        // used to set tab selected
        model.addAttribute("selectedPage", "additem");
        if(sessionUser.getUserRole().equals(Roles.ADMIN)) {
            return "additem";
        } else {
            model.addAttribute("errorMessage", "You must be an admin to add items");
            return "home";
        }
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST) 
    public String AddNewItem(@RequestParam(value = "action", required=false) String action,
            @RequestParam(value = "itemName", required=false) String itemName,
            @RequestParam(value = "itemQuantity", required=false) Integer itemQuantity, 
            @RequestParam(value = "itemPrice", required=false) Double itemPrice,
            Model model, HttpSession session) {
        
        User sessionUser = getSessionUser(session);

        if(sessionUser.getUserRole().equals(Roles.ADMIN) && action.equals("addNewItem")) {
            Item item = new Item();
            item.setName(itemName);
            item.setQuantity(itemQuantity);
            item.setPrice(itemPrice);
            shoppingService.addItem(item);
            model.addAttribute("message", "Item " + itemName + " added to the database");
        } else {
            model.addAttribute("errorMessage", "Unable to add item please make sure you a admin user");
            return "home";
        } 

        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());
        
        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "additem";
    }
    
    @RequestMapping(value = "/delItem", method = RequestMethod.GET)
    public String DeleteItem(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());
        
        if(sessionUser.getUserRole().equals(Roles.ADMIN)) {
            model.addAttribute("items", shoppingService.getAviliableItems());
            return "delitem";
        } else {
            model.addAttribute("errorMessage", "You must be an admin to delete items");
            return "home";
        }
    }

    @RequestMapping(value = "/delItem", method = RequestMethod.POST)
    public String DeleteItem(@RequestParam(value = "itemId", required=true) Long itemId,
            @RequestParam(value = "action", required=false) String action,
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());        

        if(sessionUser.getUserRole().equals(Roles.ADMIN) && action.equals("deleteItem")) {
            if(shoppingService.ItemExistsId(itemId)) {
                model.addAttribute("message", "Item Deleted");
                shoppingService.removeItemById(itemId);
            } else {
                model.addAttribute("errorMessage", "Item does not exist can't delete item that doesn't exist");
            }

            return "delitem";
        } else {
            model.addAttribute("errorMessage", "Unable to remove item please make sure you a admin user and the action is set to deleteItem");
            return "home";
        } 
    }

    @RequestMapping(value = "/contact", method = {RequestMethod.GET, RequestMethod.POST})
    public String contactCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);        
        model.addAttribute("currentUser", sessionUser.getUsername());
        Item item = new Item();
        item.setName("Pupper");
        item.setPrice(40.00);
        item.setQuantity(40);
        shoppingService.addItem(item);
        
        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        return "contact";
    }
    
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        model.addAttribute("error", e);
        return "error"; // default friendly exception message for sessionUser
    }


}
