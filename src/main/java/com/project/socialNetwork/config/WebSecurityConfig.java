package com.project.socialNetwork.config;

import com.project.socialNetwork.domain.User;
import com.project.socialNetwork.repo.UserRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**","/error/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepo userRepo){
        return map ->{
            String id = (String) map.get("sub");

           User currentUser = userRepo.findById(id).orElseGet(()->{
                User user = new User();

                user.setId(id);
                user.setName((String) map.get("name"));
                user.setUserPic((String)map.get("picture"));
                user.setEmail((String)map.get("email"));
                user.setGender((String)map.get("gender"));
                user.setLocale((String)map.get("locale"));
                return user;
            });

           currentUser.setLastVisit(LocalDateTime.now());

            return userRepo.save(currentUser);
        };
    }

}
