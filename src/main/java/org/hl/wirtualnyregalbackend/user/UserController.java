package org.hl.wirtualnyregalbackend.user;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.user.dto.UserPageResponse;
import org.hl.wirtualnyregalbackend.user.model.UserFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
class UserController {

    private final UserQueryService query;

    @GetMapping
    public UserPageResponse findUsers(UserFilter filter, Pageable pageable) {
        return query.findUsers(filter, pageable);
    }

}
