package com.sensilabs.projecthub.user.management;

import com.sensilabs.projecthub.user.management.forms.CreateUserForm;
import com.sensilabs.projecthub.user.management.forms.EditUserForm;
import com.sensilabs.projecthub.user.management.repository.UserManagementRepository;
import com.sensilabs.projecthub.user.management.service.UserManagementService;
import com.sensilabs.projecthub.user.management.service.UserManagementServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class UserManagementServiceTest {

    UserManagementRepository repository = new UserManagementRepositoryMock();
    UserManagementService service = new UserManagementServiceImpl(repository);

    @Test
    void getTest() {
        User userSaved = service.save(new CreateUserForm("Kacper", "Koncki"));
        User userFound = service.get(userSaved.getId());
        Assertions.assertEquals(userSaved.getId(), userFound.getId());
        Assertions.assertEquals(userFound.getFirstName(), "Kacper");
        Assertions.assertEquals(userFound.getLastName(), "Koncki");
    }

    @Test
    void saveTest() {
        Instant currentDate = Instant.now();
        User user = service.save(new CreateUserForm("Kacper", "Koncki"));
        Instant dateAfterSave = Instant.now();
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getFirstName(), "Kacper");
        Assertions.assertEquals(user.getLastName(), "Koncki");
        Assertions.assertTrue(currentDate.isBefore(user.getCreatedOn()));
        Assertions.assertTrue(dateAfterSave.isAfter(user.getCreatedOn()));
    }

    @Test
    void updateTest() {
        Instant currentDate = Instant.now();
        User userSaved = service.save(new CreateUserForm("Kacper", "Koncki"));
        userSaved = service.update(new EditUserForm(userSaved.getId(), "Andrzej", "Kowalski"));
        Instant dateAfterUpdate = Instant.now();
        Assertions.assertEquals(userSaved.getFirstName(), "Andrzej");
        Assertions.assertEquals(userSaved.getLastName(), "Kowalski");
        Assertions.assertTrue(currentDate.isBefore(userSaved.getCreatedOn()));
        Assertions.assertTrue(dateAfterUpdate.isAfter(userSaved.getCreatedOn()));
    }

    @Test
    void blockTest() {
        User user = service.save(new CreateUserForm("Kacper", "Koncki"));
        service.block(user.getId());
        Assertions.assertTrue(user.isBlocked());
    }

    @Test
    void unBlockTest() {
        User user = service.save(new CreateUserForm("Kacper", "Koncki"));
        service.block(user.getId());
        service.unBlock(user.getId());
        Assertions.assertFalse(user.isBlocked());
    }

    @Test
    void deleteTest() {
        User user = service.save(new CreateUserForm("Kacper", "Koncki"));
        service.delete(user.getId());
        Assertions.assertThrows(RuntimeException.class, () -> service.get(user.getId()));
    }
}
