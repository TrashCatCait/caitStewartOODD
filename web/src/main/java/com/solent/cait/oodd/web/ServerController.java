package com.solent.cait.oodd.web;

import java.io.PrintWriter;
import java.util.UUID;
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
import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dto.Invoice; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.solent.cait.oodd.dto.Item;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author caitlyn
 */

@Controller
@RequestMapping("/")
public class ServerController {
    final static Logger LOG = LogManager.getLogger(ServerController.class);

    
    @Autowired
    ShoppingService shoppingService = null;
    
    @Autowired 
    UserBasket userBasket = null;

    @Autowired
    InvoiceRepository invoiceRepo;

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

    @RequestMapping(value = "/basket", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String Basket (@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemId", required = false) Long itemId,
            @RequestParam(name = "itemUUID", required = false) String uuid,
            Model model,
            HttpSession session) {
        User sessionUser = getSessionUser(session); 
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        String message = "";
        String errorMessage = "";
        
        if(action != null) {
            if(action.equals("removeItem")) {
                userBasket.removeItem(uuid);
            } else if (action.equals("checkout")) {
                if (userBasket.getCurrentBasketItems().isEmpty()) {
                    errorMessage = "Error can't checkout an empty basket";
                } else if (sessionUser.getUserRole().equals(Roles.ANONYMOUS) || sessionUser.getUserRole().equals(Roles.DEACTIVATED)) {
                    errorMessage = "Sorry only customers and admins are allowed to use the shopping Service";
                } else {
                    message = "Checking out now";
                    if(shoppingService.purchaseItems(userBasket.getCurrentBasketItems(), userBasket.getTotal(), sessionUser)) {
                        userBasket = WebFactory.getNewBasket();
                    } else {
                        errorMessage = "An error occured at checkout. Please Make sure the item exists";
                    }
                }
            }
        }

        List<Item> baksetItems = userBasket.getCurrentBasketItems();

        Double totalPrice = userBasket.getTotal();

        // populate model with values
        model.addAttribute("basketItems", baksetItems);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("basketTotal", totalPrice);
        return "basket";
    }

    @RequestMapping(value = "/viewOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewOrder(@RequestParam(name = "order", required=false) Long id, 
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);

        Optional<Invoice> invoice = invoiceRepo.findById(id);

        if(invoice.isPresent()) {
            //Get the user name of the purchaser if it's this account or if this account is an admin let them view the order.
            if(invoice.get().getPurchaser().getUsername().equals(sessionUser.getUsername()) || sessionUser.getUserRole().equals(Roles.ADMIN)) {
                model.addAttribute("orderDetails", invoice.get());
                return "vieworder";
            } else {
                //ekse print error
                model.addAttribute("errorMessage", "Error trying to view an order that doesn't belong to your account and the current account is not admin. Only admin users can view others orders");
                return "home";
            }
        } else {
            model.addAttribute("errorMessage", "This order was not found please enter a real order");
            return "home";
        }
    }

    @RequestMapping(value = "/modifyorder", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewOrder(@RequestParam(name = "order", required=false) Long id, 
            @RequestParam(name = "order", required=false) Long id, 
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
    
    }
    @RequestMapping(value = "/orders", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String orders(Model model, HttpSession session) { 
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        //Function only for admins
        if(sessionUser.getUserRole().equals(Roles.ADMIN)) { 

            List<Invoice> invoices = invoiceRepo.findAll();
            model.addAttribute("orders", invoices);

            return "orders";
        } else {
            return "home";
        }
    }


    @RequestMapping(value = "/myorders", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String myOrders(@RequestParam(name = "user", required = false) String username,
            Model model, HttpSession session) {
        try {
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        
        List<Invoice> invoices = invoiceRepo.FindByUser(sessionUser);
        model.addAttribute("orders", invoices);
        if(!username.equals(sessionUser.getUsername())) {
            model.addAttribute("errorMessage", "Error only the owner of " + username + " Account can view their orders, The current account is " + sessionUser.getUsername());
            return "home";
        }

        return "myorders"; 
        } catch (Exception ex) {
            LOG.warn(ex);
            model.addAttribute("error message", ex.getMessage());
            return "home";
        }
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvhome(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemId", required = false) Long itemId,
            @RequestParam(name = "itemUUID", required = false) String uuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        String message = "";
        String errorMessage = "";
        

        if(action != null) {
            if(action.equals("addToCart")) {
                Item item = shoppingService.ItemAddedToBasket(itemId);
                Item newitem = new Item();
                newitem.setId(item.getId());
                newitem.setName(item.getName());
                newitem.setUuid(item.getUuid());
                newitem.setPrice(item.getPrice());
                newitem.setQuantity(1);
                if(newitem != null) {
                    userBasket.addItemToBasket(newitem);
                    message = "Item added to basket";
                } else {
                    errorMessage = "Unknow Item Error";
                }
            }  
        }

        List<Item> availableItems = shoppingService.getAviliableItems();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
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
            item.setUuid(UUID.randomUUID().toString());
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
        
        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        return "contact";
    }
    
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        LOG.warn(e);
        model.addAttribute("error", e.getCause());
        return "error"; // default friendly exception message for sessionUser
    }


}
