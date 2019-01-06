package com.crater.api.security;

import com.crater.api.database.TenantContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    private final JwtConfig jwtConfig;

    @Autowired
    public TenantInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String bearerToken = request.getHeader(jwtConfig.getAuthorizationHeader());
        String jwtToken = bearerToken.replace(jwtConfig.getJwtPrefix(), "");
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(jwtToken).getBody();
        String appId = (String) claims.get("APP_ID");
        TenantContext.setCurrentTenant(appId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
