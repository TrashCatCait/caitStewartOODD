package com.solent.cait.oodd.web;

import java.util.UUID;
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
import com.solent.cait.oodd.dao.InvoiceRepository;
import com.solent.cait.oodd.dto.Invoice;
import com.solent.cait.oodd.dto.InvoiceStatus;
import com.solent.cait.oodd.bank.dto.CreditCard;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.solent.cait.oodd.dto.Item;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.solent.cait.oodd.service.CardChecker;

/**
 *
 * @author caitlyn Class to control the server routing for the main shopping
 * application
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

    /**
     *
     * @param session
     * @return
     */
    private User getSessionUser(HttpSession session) {
        //Get http user session and cast it to user type 
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            //If a user isn't logged om produce a blank user
            user = new User();
            user.setUsername("anonymous");
            user.setUserRole(Roles.ANONYMOUS);
            session.setAttribute("sessionUser", user);
            return user;
        } else {
            return user;
        }
    }

    /**
     *
     * @param model
     * @return
     */
    //Set up root of index
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvroot(Model model) {
        return "redirect:/home";
    }

    /**
     *
     * @param action
     * @param itemIdStr
     * @param uuid
     * @param searchTxt
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String srvhome(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemId", required = false) String itemIdStr,
            @RequestParam(name = "itemUUID", required = false) String uuid,
            @RequestParam(name = "searchTxt", required = false) String searchTxt,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        String message = "";
        String errorMessage = "";
        Long itemId = null;

        if (itemIdStr != null) {
            try {
                itemId = Long.parseLong(itemIdStr);
            } catch (Exception ex) {
                model.addAttribute("errorMessage", "Unable to parse item ID");
                return "home";
            }
        }

        List<Item> availableItems = shoppingService.getAviliableItems();

        if (action != null) {

            if (action.equals("addToCart")) {
                if (itemId != null) {
                    Item item = shoppingService.getItemById(itemId);
                    Item newitem = new Item();
                    newitem.setId(item.getId());
                    newitem.setName(item.getName());
                    newitem.setUuid(item.getUuid());
                    newitem.setPrice(item.getPrice());
                    newitem.setQuantity(1);
                    if (newitem != null) {
                        userBasket.addItemToBasket(newitem);
                        message = "Item added to basket";
                    } else {
                        errorMessage = "Unknow Item Error";
                    }
                }
            } else if (action.equals("search")) {
                if (searchTxt != null) {
                    availableItems = shoppingService.getItemsByString(searchTxt);
                }
            }
        }

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "home";
    }

    /**
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String about(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
    }

    /**
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String AddNewItem(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        // used to set tab selected
        model.addAttribute("selectedPage", "additem");
        if (sessionUser.getUserRole().equals(Roles.ADMIN)) {
            return "additem";
        } else {
            model.addAttribute("errorMessage", "You must be an admin to add items");
            return "home";
        }
    }

    /**
     *
     * @param action
     * @param itemName
     * @param itemQuantityStr
     * @param itemPriceStr
     * @param itemType
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public String AddNewItem(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "itemQuantity", required = false) String itemQuantityStr,
            @RequestParam(value = "itemPrice", required = false) String itemPriceStr,
            @RequestParam(value = "itemType", required = false) String itemType,
            Model model, HttpSession session) {

        User sessionUser = getSessionUser(session);
        Integer itemQuantity;
        Double itemPrice;

        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        try {
            itemQuantity = Integer.parseInt(itemQuantityStr);
            itemPrice = Double.parseDouble(itemPriceStr);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unable to Parse item price and Item Quantity");
            return "home";
        }

        if (sessionUser.getUserRole().equals(Roles.ADMIN) && action.equals("addNewItem")) {
            Item item = new Item();
            item.setName(itemName);
            item.setQuantity(itemQuantity);
            item.setPrice(itemPrice);
            item.setUuid(UUID.randomUUID().toString());
            item.setType(itemType);
            shoppingService.addItem(item);
            model.addAttribute("message", "Item " + itemName + " added to the database");
        } else {
            model.addAttribute("errorMessage", "Unable to add item please make sure you a admin user");
            return "home";
        }

        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "additem";
    }

    /**
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/delItem", method = RequestMethod.GET)
    public String DeleteItem(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        if (sessionUser.getUserRole().equals(Roles.ADMIN)) {
            model.addAttribute("items", shoppingService.getAviliableItems());
            return "delitem";
        } else {
            model.addAttribute("errorMessage", "You must be an admin to delete items");
            return "home";
        }
    }

    /**
     *
     * @param itemIdStr
     * @param action
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/delItem", method = RequestMethod.POST)
    public String DeleteItem(@RequestParam(value = "itemId", required = true) String itemIdStr,
            @RequestParam(value = "action", required = false) String action,
            Model model, HttpSession session) {

        Long itemId;

        try {
            itemId = Long.parseLong(itemIdStr);
        } catch (Exception ex) {
            LOG.warn(ex);
            model.addAttribute("errorMessage", "Unable to parse item id");
            return "home";
        }

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        if (sessionUser.getUserRole().equals(Roles.ADMIN) && action.equals("deleteItem")) {
            if (shoppingService.ItemExistsId(itemId)) {
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

    /**
     *
     * @param model
     * @param session
     * @return
     */
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

    /**
     *
     * @param action
     * @param cvv
     * @param cardNum
     * @param cardExpire
     * @param nameOnCard
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/checkout", method = {RequestMethod.GET, RequestMethod.POST})
    public String Checkout(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "cvv", required = false) String cvv,
            @RequestParam(name = "cardNum", required = false) String cardNum,
            @RequestParam(name = "cardExpire", required = false) String cardExpire,
            @RequestParam(name = "nameOnCard", required = false) String nameOnCard,
            Model model, HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("user", sessionUser);

        if (sessionUser.getUserRole().equals(Roles.ANONYMOUS)) {
            model.addAttribute("errorMessage", "Sorry this page is only for customers");
            return "home";
        }
        CardChecker checker = new CardChecker();
        /*Very Basic Credit Card Checker*/
        if (action != null) {
            if (action.equals("checkout")) {
                LOG.info("Checkout called");
                if(userBasket.getCurrentBasketItems().isEmpty()) {
                    model.addAttribute("errorMessage", "You need items in your baset to checkout");
                    return "checkout";
                }
                if (cardNum == null) {
                    model.addAttribute("errorMessage", "Blank Card Number");
                    return "checkout";
                }
                if (cvv == null) {
                    model.addAttribute("errorMessage", "Blank Cvv");
                    return "checkout";
                }
                if (cardExpire == null) {
                    model.addAttribute("errorMessage", "Blank Expire date");
                    return "checkout";
                }
                if (nameOnCard == null || nameOnCard.length() == 0) {
                    model.addAttribute("errorMessage", "Blank name on card");
                    return "checkout";
                }
                if (!checker.isValidCard(cardNum)) {
                    model.addAttribute("errorMessage", "Error Card number invaild please make sure your card is a valid VISA CARD");
                    return "checkout";
                }
                CreditCard card = new CreditCard();
                if (cardNum.length() != 16) {
                    model.addAttribute("errorMessage", "Error card number to short");
                    return "checkout";
                }
                if (!(checker.checkCVV(cvv))) {
                    model.addAttribute("errorMessage", "Invalid CVV");
                    return "checkout";
                }
                //The only cards I can find lay this out like 03/99 so five chars
                if (!(checker.checkExpire(cardExpire))) {
                    model.addAttribute("errorMessage", "Invalid card Data");
                    return "checkout";
                }
                CreditCard userCard = new CreditCard();
                userCard.setCardnumber(cardNum);
                userCard.setCvv(cvv);
                userCard.setEndDate(cardExpire);
                userCard.setName(nameOnCard);
                userCard.setIssueNumber("01");
                if(shoppingService.purchaseItems(userBasket.getCurrentBasketItems(), userBasket.getTotal(), sessionUser, userCard)) {
                    LOG.info(userCard);
                    userBasket = WebFactory.getNewBasket();
                    model.addAttribute("message", "Checkout Successful");
                    return "home";
                }
                model.addAttribute("errorMessage", "Not Enough stock to make purchase");
            }
        }
        return "checkout";
    }

    /**
     *
     * @param action
     * @param itemIdStr
     * @param uuid
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/basket", method = {RequestMethod.GET, RequestMethod.POST})
    public String Basket(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemId", required = false) String itemIdStr,
            @RequestParam(name = "itemUUID", required = false) String uuid,
            Model model,
            HttpSession session) {
        Long itemId;
        if (itemIdStr != null) {
            try {
                itemId = Long.parseLong(itemIdStr);
            } catch (Exception ex) {
                model.addAttribute("errorMessage", "Unable to parse ID");
                return "home";
            }
        }

        User sessionUser = getSessionUser(session);
        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        String message = "";
        String errorMessage = "";

        if (action != null) {
            if (action.equals("removeItem")) {
                userBasket.removeItem(uuid);
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

    /**
     *
     * @param idstr
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/viewOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewOrder(@RequestParam(name = "order", required = false) String idstr,
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        Long id;
        try {
            id = Long.parseLong(idstr);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unable to parse ID");
            return "home";
        }

        Optional<Invoice> invoice = invoiceRepo.findById(id);

        if (invoice.isPresent()) {
            //Get the user name of the purchaser if it's this account or if this account is an admin let them view the order.
            if (invoice.get().getPurchaser().getUsername().equals(sessionUser.getUsername()) || sessionUser.getUserRole().equals(Roles.ADMIN)) {
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

    /**
     *
     * @param idstr
     * @param status
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifyOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewOrder(@RequestParam(name = "orderId", required = false) String idstr,
            @RequestParam(name = "orderStatus", required = false) String status,
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        Long id;

        try {
            id = Long.parseLong(idstr);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unable to parse ID");
            return "home";
        }

        try {
            Optional<Invoice> invoice = invoiceRepo.findById(id);
            if (invoice.isPresent()) {
                if (invoice.get().getPurchaser().getUsername().equals(sessionUser.getUsername()) || sessionUser.getUserRole().equals(Roles.ADMIN)) {
                    invoice.get().setStatus(InvoiceStatus.valueOf(status));
                    model.addAttribute("orderDetails", invoice.get());
                    invoiceRepo.save(invoice.get());
                    return "vieworder";
                } else {
                    model.addAttribute("errorMessage", "Sorry you aren't allowed to view others orders if your not an admin");
                    return "home";
                }
            } else {
                model.addAttribute("errorMessage", "Error this order doesn't seem to exist");
                return "home";
            }
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Unable to parse status");
            return "home";
        }

    }

    /**
     *
     * @param action
     * @param itemIdStr
     * @param itemName
     * @param itemQuantityStr
     * @param itemPriceStr
     * @param itemType
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateItem", method = {RequestMethod.GET, RequestMethod.POST})
    public String UpdateItems(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "itemId", required = false) String itemIdStr,
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "itemQuantity", required = false) String itemQuantityStr,
            @RequestParam(value = "itemPrice", required = false) String itemPriceStr,
            @RequestParam(value = "itemType", required = false) String itemType,
            Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("currentUser", sessionUser.getUsername());

        Long itemId;
        Integer itemQuantity;
        Double itemPrice;
        Item item;
        if (sessionUser.getUserRole().equals(Roles.ADMIN)) {
            if (action != null) {
                try {
                    itemId = Long.parseLong(itemIdStr);
                    if (action.equals("findItem")) {
                        item = shoppingService.getItemById(itemId);
                        if (item == null) {
                            model.addAttribute("errorMessage", "Item not found");
                        } else {
                            model.addAttribute("newItem", item);
                        }
                    } else if (action.equals("updateItem")) {
                        item = shoppingService.getItemById(itemId);
                        if (item == null) {
                            model.addAttribute("errorMessage", "Item not found");
                            return "home";
                        } else {
                            try {
                                itemPrice = Double.parseDouble(itemPriceStr);
                                itemQuantity = Integer.parseInt(itemQuantityStr);
                                item.setName(itemName);
                                item.setPrice(itemPrice);
                                item.setQuantity(itemQuantity);
                                item.setType(itemType);
                                shoppingService.addItem(item);
                                model.addAttribute("message", "item updated");
                            } catch (Exception ex) {
                                LOG.warn(ex);
                                model.addAttribute("errorMessage", "Unable to parse item Quantity: " + itemQuantityStr + " Or item Price" + itemPriceStr);

                            }
                        }
                    }
                } catch (Exception ex) {
                    LOG.warn(ex);
                    model.addAttribute("errorMessage", "Unable to parse item id: " + itemIdStr);
                    return "home";
                }

            }

            model.addAttribute("items", shoppingService.getAviliableItems());
            return "updateitems";
        } else {
            model.addAttribute("errorMessage", "You must be an admin to update items");
            return "home";
        }
    }

    /**
     *
     * @param model
     * @param session
     * @param searchStr
     * @param action
     * @return
     */
    @RequestMapping(value = "/orders", method = {RequestMethod.GET, RequestMethod.POST})
    public String orders(Model model, HttpSession session,
            @RequestParam(value = "searchStr", required = false) String searchStr,
            @RequestParam(value = "action", required = false) String action) {
        User sessionUser = getSessionUser(session);

        model.addAttribute("currentUser", sessionUser.getUsername());
        model.addAttribute("sessionUser", sessionUser);
        //Function only for admins
        if (sessionUser.getUserRole().equals(Roles.ADMIN)) {

            List<Invoice> invoices = invoiceRepo.findAll();

            if (action != null) {
                if (searchStr == null) {
                    model.addAttribute("errorMessage", "Please enter a username to search by");
                } else if (action.equals("search")) {
                    List<Invoice> userInvoices = invoiceRepo.FindByUsername(searchStr);
                    List<Invoice> uuidInvoices = invoiceRepo.FindByInoviceNum(searchStr);
                    invoices = Stream.of(userInvoices, uuidInvoices).flatMap(Collection::stream).collect(Collectors.toList());
                } else {
                    LOG.warn("Unknown Action on orders page actionName: " + action);
                    model.addAttribute("errorMessage", "Unknown action used on Orders page please use a known action as action: " + action + " isn't valid for this page");
                }
            }

            model.addAttribute("orders", invoices);

            return "orders";
        } else {
            return "home";
        }
    }

    /**
     *
     * @param username
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/myorders", method = {RequestMethod.GET, RequestMethod.POST})
    public String myOrders(@RequestParam(name = "user", required = false) String username,
            Model model, HttpSession session) {
        try {
            User sessionUser = getSessionUser(session);

            model.addAttribute("currentUser", sessionUser.getUsername());
            model.addAttribute("sessionUser", sessionUser);

            List<Invoice> invoices = invoiceRepo.FindByUsername(sessionUser.getUsername());
            model.addAttribute("orders", invoices);
            if (!username.equals(sessionUser.getUsername()) && !sessionUser.getUserRole().equals(Roles.ADMIN)) {
                model.addAttribute("errorMessage", "Error only the owner of " + username + " Account can view their orders, The current account is " + sessionUser.getUsername());
                return "home";
            }

            return "myorders";
        } catch (Exception ex) {
            LOG.warn(ex);
            model.addAttribute("errorMessage", ex.getMessage());
            return "home";
        }
    }

    /**
     *
     * @param e
     * @param model
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        LOG.warn(e);
        LOG.warn(e.getMessage());
        LOG.warn(e.getCause());
        LOG.warn(e.getClass());
        LOG.warn(e.getStackTrace());
        LOG.warn(e.getLocalizedMessage());
        model.addAttribute("error", e);
        return "error"; // default friendly exception message for sessionUser
    }

}
