package com.sid.security_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAutorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Allow-Headers, Access-Control-Allow-Method, Authorization");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Methods","GET,OPTIONS,POST,DELETE,PATCH");

            if (httpServletRequest.getMethod().equals("OPTIONS")) {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }

            else if (httpServletRequest.getRequestURI().equals("/login") || httpServletRequest.getRequestURI().equals("/register")) {
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }

            else {

                String jwt = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);

                if (jwt == null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }

                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET)
                        .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
                        .getBody();


                String username = claims.getSubject();

                ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                });

                UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }