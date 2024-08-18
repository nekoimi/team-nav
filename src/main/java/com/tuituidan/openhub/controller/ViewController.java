package com.tuituidan.openhub.controller;

import com.tuituidan.openhub.util.RequestUtils;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * IndexController.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2021/2/21
 */
@Controller
public class ViewController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public ViewController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    /**
     * login
     *
     * @return String
     */
    @GetMapping("/login")
    public String login(Model model) {
        HttpServletRequest request = RequestUtils.getRequest();
        Object error = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (error instanceof Exception) {
            request.setAttribute("errorMsg", ((Exception) error).getMessage());
            // 只使用一次就移除，再刷新页面就不显示了
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        // 添加oauth2-providers信息
        if (clientRegistrationRepository instanceof Iterable) {
            List<ClientRegistration> oauth2 = new ArrayList<>();
            Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
            clientRegistrations.forEach(oauth2::add);
            model.addAttribute("oauth2", oauth2);
        }

        return "login";
    }
}
