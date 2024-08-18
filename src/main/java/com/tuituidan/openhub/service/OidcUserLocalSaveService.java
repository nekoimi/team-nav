package com.tuituidan.openhub.service;

import com.tuituidan.openhub.bean.dto.UserDto;
import com.tuituidan.openhub.bean.entity.User;
import com.tuituidan.openhub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

/**
 * @author nekoimi 2024/8/16 22:32
 * <p>
 * 扩展OidcUserService，实现本地自动创建账号功能
 */
@Slf4j
@Service
public class OidcUserLocalSaveService extends OidcUserService {
    private final UserService userService;
    private final UserRepository userRepository;

    public OidcUserLocalSaveService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        // 检查本地账号信息，并且自动创建
        checkOidcUser(oidcUser);

        return oidcUser;
    }

    /**
     * 检查本地是否存在该账号信息，如果不存在，新创建一个账号
     *
     * @param oidcUser
     */
    protected void checkOidcUser(OidcUser oidcUser) {
        OidcUserInfo userInfo = oidcUser.getUserInfo();
        Assert.notNull(userInfo, "oidc用户信息为null");
        Map<String, Object> claims = userInfo.getClaims();
        Assert.notNull(claims, "oidc用户Claims为null");
        String ssoUserId = (String) claims.get("sub");
        String username = (String) claims.get("preferred_username");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("picture");

        // 检查用户是否存在
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            // 创建新用户信息
            userService.saveNewUser(ssoUserId,
                    new UserDto()
                            .setUsername(username)
                            .setNickname(nickname)
                            .setAvatar(avatar)
                            .setStatus("1")
            );
            log.info("保存OIDC用户 {} 到本地", username);
        }
    }
}
