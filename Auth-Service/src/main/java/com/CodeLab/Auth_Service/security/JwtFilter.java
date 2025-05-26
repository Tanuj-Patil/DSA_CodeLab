package com.CodeLab.Auth_Service.security;
//
//import com.CodeLab.Auth_Service.service.AuthService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.security.Security;
//import java.util.Collections;

//@Component
//public class JwtFilter extends OncePerRequestFilter {

//    @Autowired
//    AuthService authService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String bearerToken = request.getHeader("Authorization");
//
//        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
//
//             String token = bearerToken.substring(7);
//             // We got the token now we need to validate that this token is a genuine token or not.
//
//             String credentials = authService.validateToken(token);
//
//             if(credentials == null){
//                 // I am not going to set any kind of authentication and i will return from here it self
//                 // before filtering if i am not setting any kind of authentication that
//                 // means I am rejecting the request
//                 filterChain.doFilter(request,response);
//                 return;
//
//             }
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    credentials,null, Collections.emptyList());
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//        filterChain.doFilter(request,response);
//
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//        return path.equals("/auth/token/generate") || path.equals("/auth/token/validate");
//    }
//}
