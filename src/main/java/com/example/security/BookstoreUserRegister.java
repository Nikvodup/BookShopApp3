package com.example.security;


import com.example.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;





    @Autowired
    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                 JWTUtil jwtUtil, BookstoreUserDetailsService bookstoreUserDetailsService) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;

    }

    public BookstoreUser registerNewUser(RegistrationForm registrationForm) {

        BookstoreUser userByEmail = bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail());
        BookstoreUser userByPhone = bookstoreUserRepository.findBookstoreUserByPhone(registrationForm.getPhone());


        if (userByEmail==null && userByPhone==null) {
            BookstoreUser user = new BookstoreUser();
            user.setEmail(registrationForm.getEmail());
            user.setName(registrationForm.getName());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));

            user.setPhone(registrationForm.getPhone());
            bookstoreUserRepository.save(user);
            return user;
        } else {
            return userByPhone;
        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;


    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }


    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload){
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPass(payload.getCode());
        registerNewUser(registrationForm);
        UserDetails userDetails = bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }




    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }


}
