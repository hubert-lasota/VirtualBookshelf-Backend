package org.hl.wirtualnyregalbackend.user;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfCommandService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UserDefaultConfigurer {

    private final BookshelfCommandService bookshelfCommand;

    @Transactional
    public void configure(User user) {
        bookshelfCommand.addDefaultBookshelvesToUser(user);
    }

}
