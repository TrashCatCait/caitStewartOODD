package com.solent.cait.oodd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.solent.cait.oodd.dto.User;
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
        if(Roles.ADMIN.equals(userSession.getUserRole()) || username.equals(userSession.getUsername())) {
            
            if(userRepository.findByUsername(username).isEmpty()) {
                model.addAttribute("errorMessage", "The User " + username + " could not be found please check that user exists");
                return "home";
            }
            
            model.addAttribute("user", userRepository.findByUsername(username).get(0));
            return "modifyUser";
        } else if(Roles.ANONYMOUS.equals(userSession.getUserRole())) {
            model.addAttribute("errorMessage", "You must be logged in to view user details");
        } else {
            model.addAttribute("errorMessage", "Error user details called for user: " + username + " But the logged in user " + userSession.getUsername() + " is not this user or an admin"); 
        }
        return "home";

    }
    
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }

}
