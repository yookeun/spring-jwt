package com.example.springjwt.filter;

import com.example.springjwt.config.UserDetailService;
import com.example.springjwt.util.JwtUtil;
import com.example.springjwt.util.ResultCode;
import com.example.springjwt.util.ResultJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        //아래 경로는 이 필터가 적용되지 않는다.
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        HttpSession session = request.getSession();

        //Header에서 Bearer 부분 이하로 붙은 token을 파싱한다.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            System.out.println(000);
        }

        int result = jwtUtil.checkValidToken(token);
        if (result == -1) {
            response = exceptionCall(response, "expiredToken");
            return;
        } else if (result < 0) {
            response = exceptionCall(response, "invalidToken");
            return;
        }
        username = jwtUtil.extractUsername(token);
        //invalid token check
        if (username == null) {
            System.out.println(111);
            response = exceptionCall(response, "invalidToken");
            return;
        }

        //username(userid) check
        UserDetails userDetails = null;
        try {
            userDetails = userDetailService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            System.out.println(222);
            response = exceptionCall(response, "invalidToken");
            return;
        }


        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            session.setAttribute("userId", username);

        }
        filterChain.doFilter(request, response);
    }

    private HttpServletResponse exceptionCall(HttpServletResponse response, String errorType) throws IOException {
        ResultJson resultJson = new ResultJson();
        if (errorType.equals("invalidToken")) {
            resultJson.setCode(ResultCode.INVALID_TOKEN.getCode());
            resultJson.setMsg(ResultCode.INVALID_TOKEN.getMsg());
        } else if (errorType.equals("expiredToken")) {
            resultJson.setCode(ResultCode.EXPIRED_TOKEN.getCode());
            resultJson.setMsg(ResultCode.EXPIRED_TOKEN.getMsg());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(resultJson));
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        return response;
    }
}
