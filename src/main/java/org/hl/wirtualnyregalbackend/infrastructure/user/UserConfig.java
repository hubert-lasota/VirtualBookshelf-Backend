package org.hl.wirtualnyregalbackend.infrastructure.user;

import org.hl.wirtualnyregalbackend.application.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserFacade userFacade(UserRepository userRepository) {
        return new UserFacade(userRepository);
    }

}
