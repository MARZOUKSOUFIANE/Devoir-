package com.sid.security_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.service.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser user=null;
        try {
            user=new ObjectMapper().readValue(request.getInputStream(),AppUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser= (User) authResult.getPrincipal();
        String jwt= Jwts.builder().
                setSubject(springUser.getUsername()).
                setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME)).
                signWith(SignatureAlgorithm.HS256,SecurityConstants.SECRET).
                claim("roles",springUser.getAuthorities()).
                compact();
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwt);
    }
}
