package com.solent.cait.oodd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.solent.cait.oodd.dto.User;
import com.solent.cait.oodd.dto.Address;
import com.solent.cait.oodd.dto.Roles;
import com.solent.cait.oodd.dao.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author caitlyn
 */
@Controller
@RequestMapping("/")
public class LoginControl {

    @Autowired
    UserRepository userRepository;

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

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(Model model, HttpSession session) {
        User userSession = getSessionUser(session);
        model.addAttribute("currentUser", userSession.getUsername());

        String message = "You've been logged out.";
        session.invalidate();

        model.addAttribute("message", message);
        return "home";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    @Transactional
    public String login(Model model, HttpSession session) {
        String message = "Log into site with your username";

        User userSession = getSessionUser(session);
        model.addAttribute("currentUser", userSession.getUsername());

        if (!Roles.ANONYMOUS.equals(userSession.getUserRole())) {
            String errorMessage = userSession.getUsername() + " is already logged in.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        } else {
            model.addAttribute("message", message);
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @Transactional
    public String loginUser(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "uname", required = false) String username,
            @RequestParam(value = "pword", required = false) String password,
            Model model, HttpSession session) {
        User userSession = getSessionUser(session);
        model.addAttribute("currentUser", userSession.getUsername());

        if (!Roles.ANONYMOUS.equals(userSession.getUserRole())) {
            String errorMessage = userSession.getUsername() + " is already logged in.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (username == null || username.trim().isEmpty()) {
            String errorMessage = "Please enter a valid username";
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }

        List<User> users = userRepository.findByUsername(username);

        if ("login".equals(action)) {
            if (users.isEmpty()) {
                model.addAttribute("errorMessage", "unable to find user with name of " + username);
                return "login";
            } else if (password == null || !users.get(0).isValidPassword(password)) {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "login";
            }
            if (!users.get(0).getEnabled() || users.get(0).getUserRole().equals(Roles.DEACTIVATED)) {
                model.addAttribute("errorMessage", "Sorry Account " + username + " has been deactivated");
                return "login";
            }

            session.setAttribute("sessionUser", users.get(0));
            model.addAttribute("message", "User " + username + " Logged in!");
            model.addAttribute("errorMessage", "");

        } else {
            model.addAttribute("errorMessage", "Error Unknown Action requested on login page please use log in action not action of: " + action);
        }
        return "home";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    @Transactional
    public String register(Model model, HttpSession session) {
        String message = "register new user";
        String errorMessage = "";

        User userSession = getSessionUser(session);
        model.addAttribute("sessionUser", userSession);
        model.addAttribute("currentUser", userSession.getUsername());
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        return "register";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @Transactional
    public String CreateNewUser(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "uname", required = false) String username,
            @RequestParam(value = "pword", required = false) String password,
            @RequestParam(value = "pwordConfirm", required = false) String passwordConfirm,
            Model model,
            HttpSession session) {
        User userSession = getSessionUser(session);

        if (username == null || username.trim().isEmpty()) {
            String errorMessage = "Please enter a valid username";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }
        if (password == null || !password.equals(passwordConfirm) || password.length() < 8) {
            String errorMessage = "Passwords Do not match or are less than 8 chars";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }

        User newUser = new User();
        newUser.setUserRole(Roles.CUSTOMER);
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);

        //Return to log in screen and allow the new user to sign in
        model.addAttribute("message", "New User Created");
        return "login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @Transactional
    public String getUsers(Model model, HttpSession session) {
        User userSession = getSessionUser(session);
        model.addAttribute("sessionUser", userSession);
        model.addAttribute("currentUser", userSession.getUsername());

        if (!Roles.ADMIN.equals(userSession.getUserRole())) {
            model.addAttribute("errorMessage", "Only admin users can access User Info");
            return "home";
        }

        List<User> usersList = userRepository.findAll();

        model.addAttribute("userListSize", usersList.size());
        model.addAttribute("userList", usersList);
        model.addAttribute("selectedPage", "users");
        return "users";
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.GET)
    @Transactional
    public String UserDetails(@RequestParam(value = "user", required = false) String username,
            Model model, HttpSession session) {

        User userSession = getSessionUser(session);
        model.addAttribute("currentUser", userSession.getUsername());
        if (Roles.ADMIN.equals(userSession.getUserRole()) || username.equals(userSession.getUsername())) {

            if (userRepository.findByUsername(username).isEmpty()) {
                model.addAttribute("errorMessage", "The User " + username + " could not be found please check that user exists");
                return "home";
            }
            User user = userRepository.findByUsername(username).get(0);
            model.addAttribute("user", user);
            return "modifyUser";
        } else if (Roles.ANONYMOUS.equals(userSession.getUserRole())) {
            model.addAttribute("errorMessage", "You must be logged in to view user details");
            return "home";

        } else {
            model.addAttribute("errorMessage", "Error user details called for user: " + username + " But the logged in user " + userSession.getUsername() + " is not this user or an admin");
            return "home";

        }
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    @Transactional
    public String UpdateUserDetails(@RequestParam(value = "action", required = false) String action,
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(value = "fname", required = false) String firstName,
            @RequestParam(value = "sname", required = false) String secondName,
            @RequestParam(value = "addressLine1", required = false) String addrLine1,
            @RequestParam(value = "addressLine2", required = false) String addrLine2,
            @RequestParam(value = "houseNumber", required = false) String houseNum,
            @RequestParam(value = "postcode", required = false) String postcode,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "userRole", required = false) String userRole,
            @RequestParam(value = "accountEnabled", required = false) String accountEnabled,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
            Model model,
            HttpSession session) {

        List<User> users = userRepository.findByUsername(username);

        User currentUser = getSessionUser(session);
        model.addAttribute("currentUser", currentUser.getUsername());
        if (!username.equals(currentUser.getUsername()) && !Roles.ADMIN.equals(currentUser.getUserRole())) {
            model.addAttribute("errorMessage", "Only admins are allowed to modify others accounts");
            return "home";

        } else if (users.isEmpty()) {
            model.addAttribute("errorMessage", "User: " + username + " Not found");
            return "home";

        } else if (action.equals("modifyUser")) {
            model.addAttribute("message", "Account details updated");
            User modifyUser = users.get(0);
            modifyUser.setFirstName(firstName);
            modifyUser.setSecondName(secondName);
            Address address = new Address();
            address.setAddressLine1(addrLine1);
            address.setAddressLine2(addrLine2);
            address.setCity(city);
            address.setCountry(country);
            address.setCounty(county);
            address.setHouseNumber(houseNum);
            address.setMobile(mobile);
            address.setPostcode(postcode);
            address.setTelephone(telephone);
            modifyUser.setAddress(address);
            modifyUser = userRepository.save(modifyUser);
            return "home";

        } else if (action.equals("manageAccount") && currentUser.getUserRole().equals(Roles.ADMIN)) {
            User modifyUser = users.get(0);
            //Try catch to catch errors with unknow user roles shouldn't happen but people can inspect edit/mistakes can also be made
            try {
                modifyUser.setUserRole(Roles.valueOf(userRole));
                if (accountEnabled.equals("false") && accountEnabled != null) {
                    modifyUser.setEnabled(Boolean.FALSE);
                } else {
                    modifyUser.setEnabled(Boolean.TRUE);
                }
                userRepository.save(modifyUser);
                model.addAttribute("message", "User account updated");
                return "modifyUser";
            } catch (Exception ex) {
                model.addAttribute("errorMessage", "Error parsing user roles");
                return "home";
            }
        } else if (action.equals("disableAccount") && !currentUser.getUserRole().equals(Roles.ADMIN)) {
            User modifyUser = users.get(0);
            modifyUser.setEnabled(Boolean.FALSE);
            modifyUser.setUserRole(Roles.DEACTIVATED);
            userRepository.save(modifyUser);
            session.invalidate(); //Invalidate the session logging the user out
            model.addAttribute("message", "Goodbye your account is now disabled please contact an admin to renable it if you want.");
            return "home";
        } else if (action.equals("updatePassword")) {
            if (password != null && password.length() > 7 && password.equals(passwordConfirm)) {
                User modifyUser = users.get(0);
                modifyUser.setPassword(password);
                userRepository.save(modifyUser);
                model.addAttribute("message", "User: " + username + " Password has been changed");
                return "home";
            } else {
                model.addAttribute("errorMessage", "Your password must be greater than 8 characters and must match");
                return "modifyUser";
            }
        }
        model.addAttribute("errorMessage", "Unknown action called on /modifyUser Route");
        return "home";

    }

    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        model.addAttribute("error", e.getMessage());

        return "error"; // default friendly exception message for sessionUser
    }

}
