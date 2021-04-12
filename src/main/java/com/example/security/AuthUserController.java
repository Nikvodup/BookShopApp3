package com.example.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.ContactConfirmationResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthUserController {
    private final BookstoreUserRegister userRegister;

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

     @PostMapping("/resquestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestConfirmation(@RequestBody ContactConfirmationPayload payload){
         ContactConfirmationResponse response = new ContactConfirmationResponse();
         response.setResult("result");
         return response;
     }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("result");
        return response;
    }




    @PostMapping("/reg")
    public String handleUserregistration(RegistrationForm registrationForm, Model model){
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


    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUsr",userRegister.getCurrentUser());
        return "profile";
    }

 /**  @GetMapping("/logout")
   public String handleLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        if (session != null){
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()){
            cookie.setMaxAge(0);
        }

        return "redirect:/";
    }
 **/

}
