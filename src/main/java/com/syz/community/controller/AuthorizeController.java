package com.syz.community.controller;

import com.syz.community.dto.AccessTokenDto;
import com.syz.community.dto.GithubUser;
import com.syz.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @RequestMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("115285b7216b4c2397a1");
        accessTokenDto.setClient_secret("e1a88b46e2e88d952c567a8ab5afbde35f662094");
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        System.out.println(user.getId());
        return "index";
    }
}
