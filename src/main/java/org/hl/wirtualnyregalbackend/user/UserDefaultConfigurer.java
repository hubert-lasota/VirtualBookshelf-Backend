package org.hl.wirtualnyregalbackend.user;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDefaultConfigurer {

    private final BookshelfService bookshelfService;

    public void configure(User user) {
        bookshelfService.addDefaultBookshelvesToUser(user);
    }

}
