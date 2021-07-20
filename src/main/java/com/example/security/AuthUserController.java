package com.example.security;

import com.example.data.UserUpdateData;
import com.example.security.exceptions.WrongEmailException;
import com.example.security.exceptions.WrongPhoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @GetMapping("/signin")
    public String handleSignIn(){
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }


    @PostMapping("/requestEmailConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }




    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserregistration(RegistrationForm registrationForm, Model model) throws WrongEmailException, WrongPhoneException {
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public  ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                    HttpServletResponse httpServletResponse){
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token",loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @PostMapping("/profile/edit")
    public String editProfile(
            @RequestParam("phone") String phone,
            @RequestParam("mail") String mail,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("passwordReply") String passwordReply,
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) throws MessagingException {
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        model = userRegister.editProfile(user.getBookstoreUser(), phone, mail, name, password, passwordReply, model);
        return "profile";
    }

    @RequestMapping(value="/verification_token/{token}", method=RequestMethod.GET)
    public String verificationToken(@PathVariable("token") String token){
        UserUpdateData updateData = userRegister.getUpdateUser(token);
        if(isNull(updateData)){
            return "redirect:/profile";
        }
        userRegister.updateUser(updateData);
        return "redirect:/logout";
    }



/**    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUsr",userRegister.getCurrentUser());
        return "profile";
    }  **/

//    @GetMapping("/logout")
//    public String handleLogout(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        SecurityContextHolder.clearContext();
//        if (session != null){
//            session.invalidate();
//        }
//
//        for (Cookie cookie : request.getCookies()){
//            cookie.setMaxAge(0);
//        }
//
//        return "redirect:/";
//    }
}
