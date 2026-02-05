package net.abbas.app.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import net.abbas.service.user.UserService;
import net.abbas.utill.JwtUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtFilter implements Filter {
    public static final String CURRENT_USER = "CURRENT_USER";
    private final UserService userService;
    private final JwtUtill jwtUtill;

@Autowired
    public JwtFilter(UserService userService, JwtUtill jwtUtill) {
    this.userService = userService;
    this.jwtUtill = jwtUtill;
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = getCurrentUser(request);
        if (!token.isEmpty()&& jwtUtill.validateToken(token)) {
            String username =jwtUtill.tokenToUsername(token);
            request.setAttribute(CURRENT_USER, userService.readByUsername(username));
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            response.getWriter().write("Accsess Denied");
        }

    }

    public String getCurrentUser(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String perfix = "Bearer ";
        String token = "";
        if (bearerToken != null && bearerToken.startsWith(perfix)) {
            token = bearerToken.substring(perfix.length());
        }
        return token;
    }
}
