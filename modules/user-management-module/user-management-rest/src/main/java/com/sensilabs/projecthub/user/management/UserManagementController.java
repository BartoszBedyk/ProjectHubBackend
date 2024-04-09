package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.commons.SearchForm;
import com.sensilabs.projecthub.commons.SearchResponse;
import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/test")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping
    public SearchResponse<User> search(SearchForm form) {
        return null;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") String id) {
        return userManagementService.get(id);
    }

    @PostMapping
    public User save(@RequestBody CreateUserForm createUserForm) {
        return userManagementService.save(createUserForm);
    }

    @PutMapping
    public User update(@RequestBody EditUserForm editUserForm) {
        return userManagementService.update(editUserForm);
    }

    // TODO blocked i unBlocked

    @PutMapping("/block/{id}")
    public User block(@PathVariable("id") String id) {
        return userManagementService.block(id);
    }

    @PutMapping("/unblock/{id}")
    public User unblock(@PathVariable("id") String id) {
        return userManagementService.unBlock(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        userManagementService.delete(id);
    }
}
