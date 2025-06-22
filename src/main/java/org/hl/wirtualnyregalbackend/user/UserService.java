package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


}
