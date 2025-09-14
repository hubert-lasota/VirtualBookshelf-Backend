package org.hl.wirtualnyregalbackend.user;

import org.hl.wirtualnyregalbackend.auth.entity.AuthorityName;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class UserTestDataProvider {

    private UserTestDataProvider() {
    }

    public static User getRandomUser() {
        Long randomId = ThreadLocalRandom.current().nextLong(1, 10_000);
        String randomUsername = "user_" + UUID.randomUUID();
        User user = new User(randomUsername, "password", AuthorityName.USER);
        ReflectionTestUtils.setField(user, "id", randomId);
        return user;
    }

}
