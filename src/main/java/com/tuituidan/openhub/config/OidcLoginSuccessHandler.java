package com.tuituidan.openhub.config;

import com.tuituidan.openhub.bean.entity.User;
import com.tuituidan.openhub.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author nekoimi 2024/8/18 14:28
 * <p>
 * 扩展Oidc认证成功本地用户认证置换
 */
@Component
public class OidcLoginSuccessHandler extends LoginSuccessHandler {
    @Resource
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User auth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            String username = auth2User.getAttribute("preferred_username");
            User user = (User) userService.loadUserByUsername(username);

            // 重置一下上下文认证对象
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            super.onAuthenticationSuccess(request, response, newAuthentication);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
