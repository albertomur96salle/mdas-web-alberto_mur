package com.ccm.user.user.application.usecases;

import com.ccm.user.user.application.dto.UserDTO;
import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import com.ccm.user.user.domain.services.UserCreator;
import com.ccm.user.user.domain.vo.UserId;
import com.ccm.user.user.domain.vo.UserName;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class AddUserUseCaseTest {
    @Inject
    AddUserUseCase tested;

    static UserId userId;
    static UserName userName;
    static User user;
    static UserDTO userDTO;
    static UserCreator userCreator;

    @BeforeAll
    public static void setUp() {
        userId = new UserId(1);
        userName = new UserName("keko");
        user = new User(userName, userId);
        userDTO = new UserDTO("keko", 1);
    }

    @BeforeEach
    public void setMocks() {
        userCreator = Mockito.mock(UserCreator.class);
        QuarkusMock.installMockForType(userCreator, UserCreator.class);
    }

    @Test
    public void verify_createUser_CallsToMethods() throws UserAlreadyExistsException {
        Mockito.doNothing().when(userCreator).createUser(user);

        tested.createUser(userDTO);

        verify(userCreator, times(1)).createUser(any());
    }

    @Test
    public void verify_createUser_throwsUserAlreadyExistsException () throws UserAlreadyExistsException {
        Mockito.doThrow(UserAlreadyExistsException.class).when(userCreator).createUser(any());

        assertThrows(UserAlreadyExistsException.class, () -> {
            tested.createUser(userDTO);
        });
    }
}
