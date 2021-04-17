package com.example.security;

import com.example.data.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.ContactConfirmationResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthUserController {
    private final BookstoreUserRegister userRegister;
    private final JavaMailSender javaMailSender;
    private final SmsService smsService;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, JavaMailSender javaMailSender,SmsService smsService ) {
        this.userRegister = userRegister;
        this.javaMailSender = javaMailSender;
        this.smsService = smsService;
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

         if (payload.getContact().contains("@")){
             return response;// for email
         } else {
             String smsCodeString = smsService.sendSecretCodeSms(payload.getContact());
             smsService.saveSmsCode(new SmsCode(smsCodeString, 60));// expires in 1 min

             return response;
         }
     }


  /**  @PostMapping("/resquestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleMailConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mybookstore86@mail.ru");
        message.setTo(payload.getContact());
        SmsCode smsCode = new SmsCode(smsService.generateCode(), 300);// 5 min
        smsService.saveSmsCode(smsCode);
        message.setSubject("Bookstore email verification!");
        message.setText("Verification code is" + smsCode.getCode());
        javaMailSender.send(message);
        response.setResult("true");
        return response;
    }
   **/



    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        if(smsService.verifyCode(payload.getCode())){
            response.setResult("result");
            return response;
        } else {
            if (payload.getContact().contains("@")){
                response.setResult("true");
                return response;
            } else {
                return new ContactConfirmationResponse();
            }
        }

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




    @PostMapping("/login-by-phone-number")
    @ResponseBody
    public  ContactConfirmationResponse handleLoginByPhoneNumber(@RequestBody ContactConfirmationPayload payload,
                                                    HttpServletResponse httpServletResponse){
        if(smsService.verifyCode(payload.getCode())) {
            ContactConfirmationResponse loginResponse = userRegister.jwtLoginByPhoneNumber(payload);
            Cookie cookie = new Cookie("token", loginResponse.getResult());
            httpServletResponse.addCookie(cookie);
            return loginResponse;
        } else {
            return null;
        }
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
