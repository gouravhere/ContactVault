package com.contractvault.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.contractvault.entities.Providers;
import com.contractvault.entities.User;
import com.contractvault.helpers.AppConstants;
import com.contractvault.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("OAuthAuthenticationSuccessHandler");
		// identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);
        
		DefaultOAuth2User user =(DefaultOAuth2User)authentication.getPrincipal();
		var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setEmailVerified(true);
        user1.setEnabled(true);
       // user.setPassword("dummy");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            // google
            // google attributes

            user1.setEmail(oauthUser.getAttribute("email").toString());
            user1.setProfilePic(oauthUser.getAttribute("picture").toString());
            user1.setName(oauthUser.getAttribute("name").toString());
            user1.setProviderUserId(oauthUser.getName());
            user1.setProvider(Providers.GOOGLE);
            user1.setAbout("This account is created using google.");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            // github
            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user1.setEmail(email);
            user1.setProfilePic(picture);
            user1.setName(name);
            user1.setProviderUserId(providerUserId);
            user1.setProvider(Providers.GITHUB);
            user1.setAbout("This account is created using github");
        }
        else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {

        }

        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

		/* logger.info(user.getName());
		user.getAttributes().forEach((key,value)->{
			logger.info("{}=>{}",key,value);
	});
		logger.info(user.getAuthorities().toString());
		
		//+++++data to databse++++++
		String email = user.getAttribute("email").toString();
		String name = user.getAttribute("name").toString();
		String picture = user.getAttribute("picture").toString();
		
		//create user  and save to db
		
		User user1= new User();
		user1.setEmail(email);
		user1.setName(name);
		user1.setProfilePic(picture);
		user1.setPassword("password");
		user1.setUserId(UUID.randomUUID().toString());
		user1.setProvider(Providers.GOOGLE);
		user1.setEnabled(true);
		user1.setEmailVerified(true);
		user1.setProviderUserId(user.getName());
		user1.setRoleList(List.of(AppConstants.ROLE_USER));
		user1.setAbout("This account is created using Google");
		*/
		User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
		if(user2==null) {
			logger.info("User2 not found");
			userRepo.save(user1);
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
		
        
	}

}
